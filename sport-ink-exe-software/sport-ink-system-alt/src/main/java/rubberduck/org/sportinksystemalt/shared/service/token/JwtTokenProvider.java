package rubberduck.org.sportinksystemalt.shared.service.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;
import rubberduck.org.sportinksystemalt.shared.domain.EmailVerificationToken;
import rubberduck.org.sportinksystemalt.shared.domain.RefreshToken;
import rubberduck.org.sportinksystemalt.shared.domain.TokenType;
import rubberduck.org.sportinksystemalt.shared.service.cache.TokenCacheService;

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

    private final TokenCacheService tokenCacheService;

    public JwtTokenProvider(TokenCacheService tokenCacheService) {
        this.tokenCacheService = tokenCacheService;
    }

    @Override
    public AccessToken generateAccessToken(Map<String, Object> claims, String subject) {
        return AccessToken.create(
                generateToken(claims, TokenType.ACCESS_TOKEN, subject, accessTokenExpiration),
                subject,
                Collections.singleton((String) claims.get("scopes"))
        );
    }

    @Override
    public RefreshToken generateRefreshToken(Map<String, Object> claims, String subject) {
        return RefreshToken.create(
                generateToken(claims, TokenType.REFRESH_TOKEN, subject, refreshTokenExpiration),
                subject
        );
    }

    @Override
    public EmailVerificationToken generateEmailVerificationToken(Map<String, Object> claims, String subject) {
        return EmailVerificationToken.create(
                generateToken(claims, TokenType.EMAIL_VERIFICATION_TOKEN, subject, emailVerificationTokenExpiration),
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
            checkAccessTokenInCache(extractUsername(token), token);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException malformedJwtException) {
            throw new BadCredentialsException("Invalid token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new BadCredentialsException("Expired token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BadCredentialsException("Unsupported token");
        }
    }

    private void checkAccessTokenInCache(String keyValue, String token) {
        if (tokenCacheService.getAccessToken(keyValue) == null) {
            throw new BadCredentialsException("Invalid access token");
        }
        System.out.println("Token in cache: " + tokenCacheService.getAccessToken(keyValue));
    }

    @Override
    public void invalidateAccessToken(String keyValue) {
        tokenCacheService.removeAccessToken(keyValue);
    }

    @Override
    public void cacheAccessToken(AccessToken accessToken) {
        tokenCacheService.addAccessToken(accessToken.getUsername(), accessToken.getToken(), accessTokenExpiration);
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public List<String> getAuthoritiesFromToken(String token) {
        List<?> rawList = extractAllClaims(token).get("roles", List.class);
        return rawList != null ? rawList.stream()
                .map(Object::toString)
                .toList() : Collections.emptyList();
    }

    @Override
    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    private String generateToken(Map<String, Object> claims, TokenType type, String subject, long expiration) {
        Map<String, Object> claimsWithTokenType = Map.of("type", type.toString());
        claims.putAll(claimsWithTokenType);
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    @Override
    public String extractTokenType(String token) {
        return extractAllClaims(token).get("type", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
