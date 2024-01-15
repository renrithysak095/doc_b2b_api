package com.example.test_jwt.serivec;

import com.example.test_jwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface Jwtservice {

	String extractUsername(String token);

	String creatToken(UserDetails userDetails);

	boolean isTokenValid(String token, UserDetails userDetails);


	String generateRefreshToken(Map<String, Object> extractUsername, UserDetails userDetails);
}