package rubberduck.org.sportinksystemalt.shared.common.service.cache;

public interface TokenCacheService {
    void addAccessToken(String key, String value, long expirationTime);

    void addRefreshToken(String key, String value, long expirationTime);

    String getAccessToken(String key);

    String getRefreshToken(String key);

    void removeAccessToken(String key);

    void removeRefreshToken(String key);
}
