package video.upload.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;
    private final long expirationMs;

    // Provide safe defaults for local development so the application can start
    // Default secret must be at least 256 bits for HS256 (32 bytes)
    public JwtUtil(@Value("${jwt.secret:defaultDevSecretKeyForJwtDontUseInProd012345}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationMs) {
        if (secret == null || secret.isBlank()) {
            // fallback to a fixed dev secret (not for production)
            secret = "defaultDevSecretKeyForJwtDontUseInProd012345";
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validate(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public Key getKey() {
        return this.key;
    }
}
