package com.example.authservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String token;
    private Long deptId;
    private String role;
    private String image;
    private String provider;
    private LocalDateTime cred_dt;
    private LocalDateTime last_md;
}
