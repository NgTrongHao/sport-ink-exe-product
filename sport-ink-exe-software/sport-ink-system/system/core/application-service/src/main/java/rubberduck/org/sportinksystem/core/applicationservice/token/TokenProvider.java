package rubberduck.org.sportinksystem.core.applicationservice.token;

import rubberduck.org.sportinksystem.core.domain.entity.token.AccessToken;
import rubberduck.org.sportinksystem.core.domain.entity.token.EmailVerificationToken;
import rubberduck.org.sportinksystem.core.domain.entity.token.RefreshToken;

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
}
