package com.example.test_jwt.serviceImpl;

import com.example.test_jwt.repository.UserRepository;
import com.example.test_jwt.serivec.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

	private final UserRepository userRepository;

	public UserDetailsService userDetailsService(){
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
			}
		};
	}

}
