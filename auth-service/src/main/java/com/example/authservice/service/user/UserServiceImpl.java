package com.example.authservice.service.user;

import com.example.authservice.enitity.Auth;
import com.example.authservice.enumeration.Role;
import com.example.authservice.exception.NotFoundExceptionClass;
import com.example.authservice.repository.AuthRepository;
import com.example.authservice.request.AuthRequest;
import com.example.authservice.request.ResetPassword;
import com.example.authservice.response.AuthResponse;
import com.example.commonservice.config.ValidationConfig;
import com.example.commonservice.response.ApiResponse;
import com.example.commonservice.response.DepartmentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final WebClient.Builder authClient;

    @Value("${baseURL}")
    private String baseURL;

    public UserServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder, WebClient.Builder authClient) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.authClient = authClient;
    }
    @Override
    public List<AuthResponse> getAllUsers(String token) {
        List<AuthResponse> users = authRepository.findAll().stream().map(m -> m.toDto(validateDepartment(m.getDeptId(), token)))
                .collect(Collectors.toList());
        if(!users.isEmpty()){
            return users;
        }
        throw new NotFoundExceptionClass(ValidationConfig.EMPTY_USER);
    }

    @Override
    public AuthResponse getUserById(Long userId, String token) {
        return findUserById(userId).toDto(validateDepartment(findUserById(userId).getDeptId(), token));
    }

    @Override
    public Void removeUserById(Long userId) {
        // Find User
        findUserById(userId);
        // Is existing -> Remove
        authRepository.deleteById(userId);
        return null;
    }

    @Override
    public AuthResponse updateUserById(Long userId, AuthRequest request, String token) {
        Auth auth = findUserById(userId);
        if(auth.getUsername().equalsIgnoreCase(request.getUsername())){
            throw new IllegalArgumentException(ValidationConfig.EXISTING_USERNAME);
        }
        validRole(request.getRole());
        auth.setRole(request.getRole().toUpperCase());
        auth.setUrl(baseURL + "/" + passwordEncoder.encode(request.getUsername()) + UUID.randomUUID());
        auth.setStatus(request.getStatus());
        auth.setPassword(passwordEncoder.encode(request.getPassword()));
        auth.setDeptId(request.getDeptId());
        auth.setUsername(request.getUsername());
        auth.setLast_md(LocalDateTime.now());
        return authRepository.save(auth).toDto(validateDepartment(auth.getDeptId(), token));
    }

    @Override
    public Void resetPassword(Long userId, ResetPassword request) {
        Auth auth = findUserById(userId);
        if(passwordEncoder.encode(request.getConfPw()).equalsIgnoreCase(auth.getPassword())){
            if(request.getNewPw().equalsIgnoreCase(request.getConfPw())){
                auth.setPassword(passwordEncoder.encode(request.getNewPw()));
                auth.setUrl(baseURL + "/" + passwordEncoder.encode(auth.getUsername()) + UUID.randomUUID());
                authRepository.save(auth);
                return null;
            }
        }
        throw new IllegalArgumentException(ValidationConfig.INCORRECT_PW);
    }

    @Override
    public AuthResponse approveUserById(Long userId, String token) {
        Auth auth = findUserById(userId);
        auth.setStatus(true);
        return authRepository.save(auth).toDto(validateDepartment(auth.getDeptId(), token));
    }

    @Override
    public List<AuthResponse> getAllExternalRequest(String token) {
        List<AuthResponse> users = authRepository.findAllByStatus(false).stream().map(m -> m.toDto(validateDepartment(m.getDeptId(),token)))
                .collect(Collectors.toList());
        if(!users.isEmpty()){
            return users;
        }
        throw new NotFoundExceptionClass(ValidationConfig.EMPTY_USER);
    }

    public Auth findUserById(Long userId){
        return authRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionClass(ValidationConfig.NOTFOUND_USER));
    }

    // Validate role
    public void validRole(String role){
        for (Role enumRole : Role.values()) {
            if (enumRole.name().equals(role)) {
                return;
            }
        }
        throw new IllegalArgumentException(ValidationConfig.NOT_FOUND_ROLE);
    }

    // Validate is existing Department
    public String validateDepartment(Long deptId, String token){
        ObjectMapper covertSpecificClass = new ObjectMapper();
        covertSpecificClass.registerModule(new JavaTimeModule());
        try{
            return covertSpecificClass.convertValue(Objects.requireNonNull(authClient
                    .baseUrl(baseURL)
                    .build()
                    .get()
                    .uri("api/v1/departments/{id}", deptId)
                    .headers(h -> h.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(ApiResponse.class)
                    .block()).getPayload(), DepartmentDto.class).getName();
        }catch (Exception e){
            throw new NotFoundExceptionClass(ValidationConfig.NOT_FOUND_DEPT);
        }
    }

}
