package com.example.test_jwt.serivec;

import com.example.test_jwt.dto.JwtResponse;
import com.example.test_jwt.dto.RefreshTokenRefresh;
import com.example.test_jwt.dto.SignInRequest;
import com.example.test_jwt.dto.SignUpRequest;
import com.example.test_jwt.model.User;

public interface AuthenticationService {
	User signUp(SignUpRequest signUpRequest);
	JwtResponse signIn(SignInRequest signInRequest);
	JwtResponse refreshToken(RefreshTokenRefresh refreshTokenRefresh);

}
