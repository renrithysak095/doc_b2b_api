package com.example.authservice.service.user;

import com.example.authservice.request.AuthRequest;
import com.example.authservice.request.ResetPassword;
import com.example.authservice.response.AuthResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<AuthResponse> getAllUsers(String token);
    AuthResponse getUserById(Long userId, String token);
    Void removeUserById(Long userId);
    AuthResponse updateUserById(Long userId, AuthRequest request, String token);
    Void resetPassword(Long userId, ResetPassword request);
    AuthResponse approveUserById(Long userId, String token);
    List<AuthResponse> getAllExternalRequest(String token);
}
