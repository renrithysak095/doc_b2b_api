package com.example.test_jwt.controller;

import com.example.test_jwt.dto.JwtResponse;
import com.example.test_jwt.dto.RefreshTokenRefresh;
import com.example.test_jwt.dto.SignInRequest;
import com.example.test_jwt.dto.SignUpRequest;
import com.example.test_jwt.model.User;
import com.example.test_jwt.serivec.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationService authenticationService;

	@PostMapping("/signUp")
	public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
	}


	@PostMapping("/signIn")
	public ResponseEntity<JwtResponse>signIn(@RequestBody SignInRequest signInRequest){
		return ResponseEntity.ok(authenticationService.signIn(signInRequest));
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtResponse>refresh(@RequestBody RefreshTokenRefresh refreshTokenRefresh){
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRefresh));
	}


}
