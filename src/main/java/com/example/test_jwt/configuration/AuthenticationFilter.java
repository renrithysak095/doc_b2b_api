package com.example.test_jwt.configuration;

import com.example.test_jwt.serivec.Jwtservice;
import com.example.test_jwt.serivec.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final Jwtservice jwtservice;
	private final UserService userService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final  String userEmail;
//		if (authHeader == null || !authHeader.startsWith("Bearer ")){
//			filterChain.doFilter(request, response);
//			return;
//		}
		if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer" ) ){
			filterChain.doFilter(request,response);
			return;
		}
		jwt = authHeader.substring(7);
		userEmail = jwtservice.extractUsername(jwt);
		if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null){
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
			if (jwtservice.isTokenValid(jwt,userDetails)){
				SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
				);
				authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);
				securityContext.setAuthentication(authToken);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);

	}

}
