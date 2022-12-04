package com.cursor.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("Authorization")
    private String authorizationHeader;
    @Value("there is no spoon; greed is good")
    private String secret;
    @Value("userId")
    private String userId;
    @Value("900_000")
    private int validity;
    private Key key;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    public String createToken(String username, Long id) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(userId, id);
        Date expiration = new Date(System.currentTimeMillis() + validity);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public boolean tokenIsValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT is expired or invalid!");
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", new ArrayList<>());
    }

    private String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}
