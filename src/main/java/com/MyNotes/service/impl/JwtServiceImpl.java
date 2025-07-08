package com.MyNotes.service.impl;

import com.MyNotes.entity.User;
import com.MyNotes.service.JwtService;
import com.MyNotes.util.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.access.token.secret.key}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${jwt.refresh.token.secret.key}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Value("${jwt.access.token.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh.token.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;

    @Value("${jwt.issuer}")
    private String ISSUER;

    @Override
    public String generateToken(User user, TokenType tokenType) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + getExpiration(tokenType)))
                .issuer(ISSUER)
                .signWith(getSecretKey(tokenType))
                .compact();
    }

    @Override
    public Claims verifyToken(String token, TokenType tokenType) {
        return Jwts.parser()
                .requireIssuer(ISSUER)
                .verifyWith(getSecretKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    private SecretKey getSecretKey(TokenType tokenType) {
        return Keys.hmacShaKeyFor((TokenType.ACCESS.equals(tokenType)) ? ACCESS_TOKEN_SECRET_KEY.getBytes() : REFRESH_TOKEN_SECRET_KEY.getBytes());
    }

    private Long getExpiration(TokenType tokenType) {
        return (TokenType.ACCESS.equals(tokenType)) ? ACCESS_TOKEN_EXPIRATION : REFRESH_TOKEN_EXPIRATION;
    }
}
