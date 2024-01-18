package com.example.authservice.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private final LocalDateTime dateTime;
    private final String USER_NM;
    private final String ACES_TK;
}
