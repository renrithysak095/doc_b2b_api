package com.example.authservice.service.user;

import com.example.authservice.request.AuthRequest;
import com.example.authservice.response.AuthResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<AuthResponse> getAllUsers();
    AuthResponse getUserById(Long userId);
    Void removeUserById(Long userId);
    AuthResponse updateUserById(Long userId, AuthRequest request);
}
