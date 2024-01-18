package com.example.commonservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String url;
    private Long deptId;
    private String role;
    private String image;
    private LocalDateTime cred_dt;
}
