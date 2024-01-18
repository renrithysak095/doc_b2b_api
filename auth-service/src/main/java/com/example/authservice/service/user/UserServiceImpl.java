package com.example.authservice.service.user;

import com.example.authservice.enitity.Auth;
import com.example.authservice.enumeration.Role;
import com.example.authservice.exception.NotFoundExceptionClass;
import com.example.authservice.repository.AuthRepository;
import com.example.authservice.request.AuthRequest;
import com.example.authservice.response.AuthResponse;
import com.example.commonservice.config.ValidationConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<AuthResponse> getAllUsers() {
        List<AuthResponse> users = authRepository.findAll().stream().map(Auth::toDto)
                .collect(Collectors.toList());
        if(!users.isEmpty()){
            return users;
        }
        throw new NotFoundExceptionClass(ValidationConfig.EMPTY_USER);
    }

    @Override
    public AuthResponse getUserById(Long userId) {
        return findUserById(userId).toDto();
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
    public AuthResponse updateUserById(Long userId, AuthRequest request) {
        Auth auth = findUserById(userId);
        validRole(request.getRole());
        auth.setRole(request.getRole());
        auth.setStatus(request.getStatus());
        auth.setPassword(passwordEncoder.encode(request.getPassword()));
        auth.setDeptId(request.getDeptId());
        auth.setUsername(request.getUsername());
        auth.setLast_md(LocalDateTime.now());
        return authRepository.save(auth).toDto();
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

}
