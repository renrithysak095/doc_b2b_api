package com.example.authservice.controller;

import com.example.authservice.exception.NotFoundExceptionClass;
import com.example.authservice.request.AuthRequest;
import com.example.authservice.request.UserRequest;
import com.example.authservice.response.AuthResponse;
import com.example.authservice.response.UserResponse;
import com.example.authservice.service.auth.AuthService;
import com.example.commonservice.config.ValidationConfig;
import com.example.commonservice.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication")
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticate;

    public AuthController(AuthService authService, AuthenticationManager authenticate) {
        this.authService = authService;
        this.authenticate = authenticate;
    }

    @PostMapping("/register")
    @Operation(summary = "registration")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody AuthRequest request){
        return new ResponseEntity<>(new ApiResponse<>(
                "user created successfully",
                authService.register(request),
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@Valid @RequestBody UserRequest request) throws Exception {
        authenticate(request.getUsername(),request.getPassword());
        return new ResponseEntity<>(new ApiResponse<>(
                "user logged-in successfully",
                authService.login(request),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new IllegalAccessException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new NotFoundExceptionClass(ValidationConfig.INVALID_CREDENTIALS);
        }
    }
}
