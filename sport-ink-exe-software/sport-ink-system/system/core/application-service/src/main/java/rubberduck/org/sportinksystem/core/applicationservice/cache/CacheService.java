package rubberduck.org.sportinksystem.core.applicationservice.cache;

public interface CacheService {
    void put(String key, Object value, long timeToLive);

    Object get(String key);

    void remove(String key);

    void clear();
}
