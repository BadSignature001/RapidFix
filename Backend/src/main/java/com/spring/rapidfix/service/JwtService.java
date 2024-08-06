package com.spring.rapidfix.service;

import org.springframework.stereotype.Service;

import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.repository.RidersTokenRepository;
import com.spring.rapidfix.repository.UserTokenRepository;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
	private final UserTokenRepository tokenRepository;
	private final RidersTokenRepository rtokenRepository ;

	public JwtService(UserTokenRepository tokenRepository, RidersTokenRepository rtokenRepository) {
		this.tokenRepository = tokenRepository;
		this.rtokenRepository = rtokenRepository;
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
 
	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) 
	{
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
	
	
	private SecretKey getSigninKey() 
	{
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
	
	public String generateToken(UserDetails userDetails)
	{
        String token = Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }
	
	
	public boolean isValid(String token, UserDetails user) {
		String username = extractUsername(token);

		if (username.endsWith("@rider.com")) {
			boolean validToken = rtokenRepository.findByToken(token).map(t -> !t.isLoggedOut()).orElse(false);
			return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
		} else {
			boolean validToken = tokenRepository.findByToken(token).map(t -> !t.isLoggedOut()).orElse(false);
			return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;

		}

	}

}

//s
