package rubberduck.org.sportinksystemalt.shared.common.service.token;

import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;
import rubberduck.org.sportinksystemalt.shared.domain.EmailVerificationToken;
import rubberduck.org.sportinksystemalt.shared.domain.RefreshToken;

import java.util.List;
import java.util.Map;

public interface TokenProvider {
    AccessToken generateAccessToken(Map<String, Object> claims, String subject);

    RefreshToken generateRefreshToken(Map<String, Object> claims, String subject);

    EmailVerificationToken generateEmailVerificationToken(Map<String, Object> claims, String subject);

    boolean validateToken(String token);

    String extractUsername(String token);

    List<String> getAuthoritiesFromToken(String token);

    long getAccessTokenExpiration();

    String extractTokenType(String token);
}
