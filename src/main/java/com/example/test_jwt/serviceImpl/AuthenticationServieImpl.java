package com.example.test_jwt.serviceImpl;

import com.example.test_jwt.dto.JwtResponse;
import com.example.test_jwt.dto.RefreshTokenRefresh;
import com.example.test_jwt.dto.SignInRequest;
import com.example.test_jwt.dto.SignUpRequest;
import com.example.test_jwt.model.Role;
import com.example.test_jwt.model.User;
import com.example.test_jwt.repository.UserRepository;
import com.example.test_jwt.serivec.AuthenticationService;
import com.example.test_jwt.serivec.Jwtservice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServieImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Jwtservice jwtservice;

	public User signUp(SignUpRequest signUpRequest) {
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setRole(Role.user);
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		return userRepository.save(user);
	}

	public JwtResponse signIn(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
		var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("invaill eamial and passwore"));
		var jwt = jwtservice.creatToken(user);
		var refreshToken = jwtservice.generateRefreshToken(new HashMap<>(), user);
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(jwt);
		jwtResponse.setRefrehsToken(refreshToken);
		return jwtResponse;
	}

	public JwtResponse refreshToken(RefreshTokenRefresh refreshTokenRefresh){
		String userEmail= jwtservice.extractUsername(refreshTokenRefresh.getToken());
		User user=userRepository.findByEmail(userEmail).orElseThrow();
		if (jwtservice.isTokenValid(refreshTokenRefresh.getToken(),user)){
			var jwt=jwtservice.creatToken(user);
			JwtResponse jwtResponse = new JwtResponse();
			jwtResponse.setToken(jwt);
			jwtResponse.setRefrehsToken(refreshTokenRefresh.getToken());
			return jwtResponse;
		}
		return null;
	}


}
