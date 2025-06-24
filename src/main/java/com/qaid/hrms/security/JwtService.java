package com.qaid.hrms.security;

import com.qaid.hrms.config.JwtConfig;
import com.qaid.hrms.exception.ExpiredTokenException;
import com.qaid.hrms.exception.InvalidTokenException;
import com.qaid.hrms.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Autowired
    public JwtService(JwtConfig jwtConfig, PrivateKey privateKey, PublicKey publicKey) {
        this.jwtConfig = jwtConfig;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getUserRoles());
        claims.put("userLevel", user.getUserLevel());
        return createJwtToken(claims, user.getUsername(), jwtConfig.getExpiration());
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getUserRoles());
        claims.put("userLevel", user.getUserLevel());
        return createJwtToken(claims, user.getUsername(), jwtConfig.getRefreshToken().getExpiration());
    }

    private String createJwtToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(privateKey)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractClaim(token, Claims::getSubject);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    public String extractTokenId(String token) {
        return extractClaim(token, Claims::getId);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
