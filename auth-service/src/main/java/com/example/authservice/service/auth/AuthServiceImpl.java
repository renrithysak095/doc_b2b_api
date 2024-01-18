package com.example.authservice.service.auth;
import com.example.authservice.config.jwt.JwtTokenUtils;
import com.example.authservice.enumeration.Role;
import com.example.authservice.repository.AuthRepository;
import com.example.authservice.request.AuthRequest;
import com.example.authservice.request.UserRequest;
import com.example.authservice.response.AuthResponse;
import com.example.authservice.response.UserResponse;
import com.example.commonservice.config.ValidationConfig;
import com.example.commonservice.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final WebClient.Builder authClient;

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
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return authRepository.save(request.toEntity(LocalDateTime.now(),LocalDateTime.now())).toDto();
    }

    @Override
    public UserResponse login(UserRequest request){
        UserDetails user = loadUserByUsername(request.getUsername().toLowerCase());
        String token = jwtTokenUtils.generateToken(user);
        return authRepository.getByUsername(request.getUsername().toLowerCase()).toResponse(token);
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
    public void validateDepartment(Long deptId){
//        ObjectMapper covertSpecificClass = new ObjectMapper();
//        covertSpecificClass.registerModule(new JavaTimeModule());
//        return covertSpecificClass.convertValue(Objects.requireNonNull(authClient
//                .baseUrl("http://8.222.225.41:8081/")
//                .build()
//                .get()
//                .uri("api/v1/users/{id}", id)
//                .retrieve()
//                .bodyToMono(ApiResponse.class)
//                .block()).getPayload(), User.class);
    }

}
