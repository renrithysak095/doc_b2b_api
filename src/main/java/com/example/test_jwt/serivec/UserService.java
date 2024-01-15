package com.example.test_jwt.serivec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

	UserDetailsService userDetailsService();


}
