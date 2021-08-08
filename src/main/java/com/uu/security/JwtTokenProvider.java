package com.uu.security;

import com.uu.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@ApplicationScoped
@Dependent
public class JwtTokenProvider {
    @ConfigProperty(name = "jwt.secret")
    private String JWT_SECRET;

    @ConfigProperty(name = "jwt.expired")
    private long JWT_EXPIRATION = 604800000L;

    private SecretKey key = null;

    @PostConstruct
    public void postConstruct() {
        this.key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("organizationId", user.getOrganizationId())
                .claim("profilePicture", user.getProfilePicture())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
