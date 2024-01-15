package com.example.test_jwt.serviceImpl;

import com.example.test_jwt.serivec.Jwtservice;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.management.ObjectName;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements Jwtservice {

	private final String SECRET_KEY = "8e21cf0843cd777b1b8275ec4cffee1522e44639f516401f05c7940a1e071334";
	public String extractUsername(String token){
		return extractClaim(token, Claims::getSubject);
	}
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String creatToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*6*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}

	public String generateRefreshToken(Map<String, Object> extractUsername, UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*6*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}
	private Date extractExpiration(String token){
		return extractClaim(token, Claims::getExpiration);
	}

	public boolean isTokenValid(String token, UserDetails userDetails){
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}



	private boolean isTokenExpired(String token){
		return extractExpiration(token).before(new Date());
	}



	private Claims extractAllClaims(String token){
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();

	}



	private Key getSignKey(){
		byte[]keybytes= Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keybytes);

	}

}
