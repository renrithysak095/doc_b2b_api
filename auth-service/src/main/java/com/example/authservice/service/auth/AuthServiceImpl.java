package com.example.authservice.service.auth;

import com.example.authservice.config.jwt.JwtTokenUtils;
import com.example.authservice.enitity.Auth;
import com.example.authservice.enumeration.Provider;
import com.example.authservice.enumeration.Role;
import com.example.authservice.exception.NotFoundExceptionClass;
import com.example.authservice.repository.AuthRepository;
import com.example.authservice.request.AuthRequest;
import com.example.authservice.request.UserRequest;
import com.example.authservice.response.AuthResponse;
import com.example.authservice.response.UserResponse;
import com.example.commonservice.config.ValidationConfig;
import com.example.commonservice.response.ApiResponse;
import com.example.commonservice.response.DepartmentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final WebClient.Builder authClient;

    @Value("${baseURL}")
    private String baseURL;

    public AuthServiceImpl(JwtTokenUtils jwtTokenUtils, PasswordEncoder passwordEncoder, AuthRepository authRepository, WebClient.Builder authClient) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
        this.authClient = authClient;
    }
    @Override
    public UserDetails loadUserByUsername(String username){
        return authRepository.findByUsername(username);
    }

    @Override
    public AuthResponse register(AuthRequest request) {
        validRole(request.getRole());
        validateDepartment(request.getDeptId());
        validateProvider(request.getProvider());
        validateUser(request.getUsername(),request.getProvider());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return authRepository.save(request.toEntity(LocalDateTime.now(),LocalDateTime.now(), baseURL  + passwordEncoder.encode(request.getUsername()) + UUID.randomUUID())).toDto(validateDepartment(request.getDeptId()));
    }

    @Override
    public UserResponse login(UserRequest request){
        UserDetails user = loadUserByUsername(request.getUsername().toLowerCase());
        String token = jwtTokenUtils.generateToken(user);
        return authRepository.getByUsername(request.getUsername().toLowerCase()).toResponse(token);
    }

    @Override
    public UserResponse urlLogin(String url) {
        try{
            Auth auth = authRepository.findByUrl(url);
            return login(new UserRequest(auth.getUsername(),auth.getPassword()));
        }catch (Exception e){
            throw new NotFoundExceptionClass(ValidationConfig.NOTFOUND_USER);
        }
    }

    @Override
    public AuthResponse temporary(AuthRequest request) {
        validRole(request.getRole());
        validateDepartment(request.getDeptId());
        validateProvider(request.getProvider());
        for (Provider enumProvider : Provider.values()) {
            if (enumProvider.name().equalsIgnoreCase(request.getProvider().toUpperCase()) && !(request.getProvider().equalsIgnoreCase(Provider.CREDENTIALS.name()))) {
                Auth auth = authRepository.getByUsernameAndProvider(request.getUsername(),request.getProvider().toUpperCase());
                if(auth != null){
                    if(auth.getUsername().equalsIgnoreCase(request.getUsername())){
                        if(auth.getProvider().equalsIgnoreCase(request.getProvider())){
                            auth.setPassword(passwordEncoder.encode(request.getPassword()));
                            auth.setUrl(baseURL  + passwordEncoder.encode(request.getUsername()) + UUID.randomUUID());
                            return authRepository.save(auth).toDto(validateDepartment(request.getDeptId()));
                        }
                    }
                }
                return authRepository.save(request.toEntity(LocalDateTime.now(),LocalDateTime.now(), baseURL  + passwordEncoder.encode(request.getUsername()) + UUID.randomUUID())).toDto(validateDepartment(request.getDeptId()));
            }
        }
        throw new IllegalArgumentException(ValidationConfig.NOT_FOUND_PROVIDER);
    }

    // Validate role
    public void validRole(String role){
        for (Role enumRole : Role.values()) {
            if (enumRole.name().equals(role.toUpperCase())) {
                return;
            }
        }
        throw new IllegalArgumentException(ValidationConfig.NOT_FOUND_ROLE);
    }

    // Validate Provider
    public void validateProvider(String provider){
        for (Provider enumProvider : Provider.values()) {
            if (enumProvider.name().equals(provider.toUpperCase())) {
                return;
            }
        }
        throw new IllegalArgumentException(ValidationConfig.NOT_FOUND_PROVIDER);
    }

    // Validate is existing User
    public void validateUser(String username, String provider){
        Auth auth = authRepository.getByUsernameAndProvider(username, provider.toUpperCase());
        if(auth != null){
            throw new IllegalArgumentException(ValidationConfig.EXISTING_USERNAME);
        }
    }

    // Validate is existing Department
    public String validateDepartment(Long deptId){
        ObjectMapper covertSpecificClass = new ObjectMapper();
        covertSpecificClass.registerModule(new JavaTimeModule());
        try{
            return covertSpecificClass.convertValue(Objects.requireNonNull(authClient
                    .baseUrl(baseURL)
                    .build()
                    .get()
                    .uri("api/v1/departments/{id}", deptId)
                    .retrieve()
                    .bodyToMono(ApiResponse.class)
                    .block()).getPayload(), DepartmentDto.class).getName();
        }catch (Exception e){
            throw new NotFoundExceptionClass(ValidationConfig.NOT_FOUND_DEPT);
        }
    }

}
