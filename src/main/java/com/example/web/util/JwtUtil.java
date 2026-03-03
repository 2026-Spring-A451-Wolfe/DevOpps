 /**************************************************************************
 * Filename: JwtUtil.java
 * Project: Infrastructure Reporting & Tracking System
 * Description: Provides methods for generating, validating, and parsing JSON
 *              Web Tokens used for secure authentication.
 * Author: Sophina Nichols
 * Date Last Modified: 03/03/2026
 **************************************************************************/

package com.example.web.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final long EXPIRATION_MS = 86400000; // 24 hours
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate a token for a logged in user
    public static String generateToken(int userId, String username, String role) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Validate a token and return the claims
    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract user ID from a token
    public static int getUserId(String token) {
        return Integer.parseInt(validateToken(token).getSubject());
    }

    // Extract role from a token
    public static String getRole(String token) {
        return (String) validateToken(token).get("role");
    }
}