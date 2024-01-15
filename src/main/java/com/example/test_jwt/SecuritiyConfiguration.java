package com.example.test_jwt;

import com.example.test_jwt.configuration.AuthenticationFilter;
import com.example.test_jwt.model.Role;
import com.example.test_jwt.serivec.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecuritiyConfiguration {
		private final AuthenticationFilter authenticationFilter;
		private final UserService userService;

		@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
			http
					.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests(request->request.requestMatchers("/", "/v3/api-docs/**","/swagger-ui/**","/swagger-ui-html",
									"/api/v1/auth/**").permitAll()
							.requestMatchers("api/v1/admin").hasAnyAuthority(Role.admin.name())
							.requestMatchers("api/v1/user").hasAnyAuthority(Role.user.name())
							.anyRequest().authenticated())
					.sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authenticationProvider(authenticationProvider()).addFilterBefore(
							authenticationFilter, UsernamePasswordAuthenticationFilter.class
					);
			return http.build();

		}
		@Bean
	public AuthenticationProvider authenticationProvider(){
			DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
			authenticationProvider.setUserDetailsService(userService.userDetailsService());
			authenticationProvider.setPasswordEncoder(passwordEncoder());
			return authenticationProvider;
		}

		@Bean
	public PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}

		@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
			return configuration.getAuthenticationManager();

		}

}
