package rubberduck.org.sportinksystemalt.shared.common.service.cache.impl;

import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.shared.common.service.cache.CacheService;
import rubberduck.org.sportinksystemalt.shared.common.service.cache.TokenCacheService;

@Service
public class RedisTokenCacheService implements TokenCacheService {

    private final CacheService cacheService;

    public RedisTokenCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void addAccessToken(String key, String value, long expirationTime) {
        String accessTokenKey = "access:" + key;
        cacheService.put(accessTokenKey, value, expirationTime);
    }

    @Override
    public void addRefreshToken(String key, String value, long expirationTime) {
        String refreshTokenKey = "refresh:" + key;
        cacheService.put(refreshTokenKey, value, expirationTime);
    }

    @Override
    public String getAccessToken(String key) {
        String accessTokenKey = "access:" + key;
        return (String) cacheService.get(accessTokenKey);
    }

    @Override
    public String getRefreshToken(String key) {
        String refreshTokenKey = "refresh:" + key;
        return (String) cacheService.get(refreshTokenKey);
    }

    @Override
    public void removeAccessToken(String key) {
        String accessTokenKey = "access:" + key;
        cacheService.remove(accessTokenKey);
    }

    @Override
    public void removeRefreshToken(String key) {
        String refreshTokenKey = "refresh:" + key;
        cacheService.remove(refreshTokenKey);
    }
}
