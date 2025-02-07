package rubberduck.org.sportinksystemalt.shared.common.service.cache.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.shared.common.service.cache.CacheService;

import java.util.Objects;

@Service
public class RedisCacheService implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisCacheService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Store an object as a JSON string in the cache.
     *
     * @param key   The key to store the object under.
     * @param value The object to store in the cache.
     */
    @Override
    public void put(String key, Object value, long timeToLive) {
        try {
            // Convert the object to a JSON string and store it in Redis
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value), timeToLive);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve an object from the cache.
     *
     * @param key The key of the object to retrieve.
     * @return The object stored in the cache.
     */
    @Override
    public Object get(String key) {
        try {
            // Retrieve the object from Redis and convert it back to its original form
            return objectMapper.readValue((String) redisTemplate.opsForValue().get(key), Object.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void clear() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().serverCommands();
    }
}
