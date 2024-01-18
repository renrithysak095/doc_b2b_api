package com.example.authservice.service.auth;
import com.example.authservice.request.AuthRequest;
import com.example.authservice.request.UserRequest;
import com.example.authservice.response.AuthResponse;
import com.example.authservice.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public interface AuthService extends UserDetailsService {
    AuthResponse register(AuthRequest request);
    UserResponse login(UserRequest request) throws Exception;
    UserResponse urlLogin(String url);
}
