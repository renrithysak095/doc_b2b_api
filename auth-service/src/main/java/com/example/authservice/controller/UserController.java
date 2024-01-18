package com.example.authservice.controller;

import com.example.authservice.request.AuthRequest;
import com.example.authservice.request.ResetPassword;
import com.example.authservice.response.AuthResponse;
import com.example.authservice.service.user.UserService;
import com.example.commonservice.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "list users")
    public ResponseEntity<ApiResponse<List<AuthResponse>>> userList(){
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched all users successfully",
                userService.getAllUsers(),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "get user by id")
    public ResponseEntity<ApiResponse<AuthResponse>> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched users by id successfully",
                userService.getUserById(userId),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "updated user by id")
    public ResponseEntity<ApiResponse<AuthResponse>> updateUserById(@PathVariable Long userId,
                                                                    @RequestBody @Valid AuthRequest request){
        return new ResponseEntity<>(new ApiResponse<>(
                "updated users by id successfully",
                userService.updateUserById(userId,request),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/reset-password/{userId}")
    @Operation(summary = "reset password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@PathVariable Long userId,
                                                                   @RequestBody @Valid ResetPassword request){
        return new ResponseEntity<>(new ApiResponse<>(
                "password changed successfully",
                userService.resetPassword(userId,request),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "delete user by id")
    public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable Long userId){
        return new ResponseEntity<>(new ApiResponse<>(
                "user delete successfully",
                userService.removeUserById(userId),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }



}
