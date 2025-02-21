package rubberduck.org.sportinksystemalt.shared.service.cache.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.shared.service.cache.CacheService;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisCacheService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
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
            String dataToStore;

            if (value instanceof String) {
                dataToStore = (String) value;
            } else {
                dataToStore = objectMapper.writeValueAsString(value);
            }
            System.out.println("dataToStore: " + " + key " + key + " + dataToStore " + dataToStore + " + timeToLive " + timeToLive);
            redisTemplate.opsForValue().set(key, dataToStore, timeToLive, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Error storing key in Redis: " + key, e);
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
//        try {
//            // Retrieve the object from Redis and convert it back to its original form
//            return objectMapper.readValue((String) redisTemplate.opsForValue().get(key), Object.class);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        try {
            System.out.println("Data from Redis: " + redisTemplate.opsForValue().get(key));
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving key from Redis: " + key, e);
        }
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void clear() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().serverCommands();
        Set<String> keys = redisTemplate.keys("*");
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
