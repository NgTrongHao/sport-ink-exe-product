package rubberduck.org.sportinksystem.infrastructure.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import rubberduck.org.sportinksystem.core.applicationservice.token.TokenProvider;
import rubberduck.org.sportinksystem.core.domain.entity.token.AccessToken;
import rubberduck.org.sportinksystem.core.domain.entity.token.EmailVerificationToken;
import rubberduck.org.sportinksystem.core.domain.entity.token.RefreshToken;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access-token-expiration-in-ms}")
    private long accessTokenExpiration;

    @Value("${security.jwt.refresh-token-expiration-in-ms}")
    private long refreshTokenExpiration;

    @Value("${security.jwt.email-verification-token-expiration-in-ms}")
    private long emailVerificationTokenExpiration;

    @Override
    public AccessToken generateAccessToken(Map<String, Object> claims, String subject) {
        return AccessToken.create(
                generateToken(claims, subject, accessTokenExpiration),
                subject,
                Collections.singleton((String) claims.get("scopes"))
        );
    }

    @Override
    public RefreshToken generateRefreshToken(Map<String, Object> claims, String subject) {
        return RefreshToken.create(
                generateToken(claims, subject, refreshTokenExpiration),
                subject
        );
    }

    @Override
    public EmailVerificationToken generateEmailVerificationToken(Map<String, Object> claims, String subject) {
        return EmailVerificationToken.create(
                generateToken(claims, subject, emailVerificationTokenExpiration),
                subject
        );
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException malformedJwtException) {
            throw new BadCredentialsException("Invalid token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new BadCredentialsException("Expired token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BadCredentialsException("Unsupported token");
        }
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public List<String> getAuthoritiesFromToken(String token) {
        List<?> rawList = extractAllClaims(token).get("scopes", List.class);
        return rawList.stream()
                .map(Object::toString)
                .toList();
    }

    @Override
    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    private String generateToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
