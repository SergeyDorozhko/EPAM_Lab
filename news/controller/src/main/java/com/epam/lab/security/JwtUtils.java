package com.epam.lab.security;

import com.epam.lab.dto.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final String INVALID_JWT_SIGNATURE = "Invalid JWT signature: {}";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token: {}";
    private static final String JWT_TOKEN_IS_EXPIRED = "JWT token is expired: {}";
    private static final String JWT_TOKEN_IS_UNSUPPORTED = "JWT token is unsupported: {}";
    private static final String JWT_CLAIMS_STRING_IS_EMPTY = "JWT claims string is empty: {}";
    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.err.println(INVALID_JWT_SIGNATURE + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println(INVALID_JWT_TOKEN + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println(JWT_TOKEN_IS_EXPIRED + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println(JWT_TOKEN_IS_UNSUPPORTED + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(JWT_CLAIMS_STRING_IS_EMPTY + e.getMessage());
        }

        return false;
    }

}
