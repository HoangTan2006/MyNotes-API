package com.MyNotes.service;

import com.MyNotes.entity.User;
import com.MyNotes.util.TokenType;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(User user, TokenType tokenType);
    Claims verifyToken(String token, TokenType tokenType);
}
