package com.saucedemo.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.saucedemo.config.ConfigReader;

import java.util.Map;
import java.util.Set;

/**
 * Purpose: Redis Utility Class
 * This class provides methods to interact with Redis for caching test data and
 * session management.
 * Uses Jedis client library with connection pooling for efficient resource
 * management.
 * 
 * Features:
 * - Store and retrieve key-value pairs
 * - Store and retrieve hash maps
 * - Session token management
 * - Cache test data
 * - Connection pooling for performance
 * 
 * Note: Redis server must be running on configured host and port
 */
public class RedisUtil {

    private static ConfigReader config = ConfigReader.getInstance();
    private static JedisPool jedisPool;
    private static boolean redisAvailable = false;

    /**
     * Initialize Redis connection pool
     * Checks if Redis is enabled and server is available
     */
    public static void initializeRedis() {
        if (!config.isRedisEnabled()) {
            System.out.println("Redis is disabled in configuration");
            return;
        }

        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);

            String password = config.getRedisPassword();

            if (password != null && !password.isEmpty()) {
                jedisPool = new JedisPool(
                        poolConfig,
                        config.getRedisHost(),
                        config.getRedisPort(),
                        config.getRedisTimeout(),
                        password);
            } else {
                jedisPool = new JedisPool(
                        poolConfig,
                        config.getRedisHost(),
                        config.getRedisPort(),
                        config.getRedisTimeout());
            }

            // Test connection
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.ping();
                redisAvailable = true;
                System.out.println("Redis connection established: " +
                        config.getRedisHost() + ":" + config.getRedisPort());
            }

        } catch (JedisConnectionException e) {
            System.err.println("Redis server is not available: " + e.getMessage());
            System.err.println("Redis features will be disabled. Tests will continue without Redis.");
            redisAvailable = false;
        } catch (Exception e) {
            System.err.println("Error initializing Redis: " + e.getMessage());
            redisAvailable = false;
        }
    }

    /**
     * Check if Redis is available
     * 
     * @return true if Redis is available, false otherwise
     */
    public static boolean isRedisAvailable() {
        return redisAvailable;
    }

    /**
     * Close Redis connection pool
     */
    public static void closeRedis() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
            System.out.println("Redis connection pool closed");
        }
    }

    /**
     * Set key-value pair in Redis
     * 
     * @param key   Key
     * @param value Value
     */
    public static void set(String key, String value) {
        if (!redisAvailable) {
            System.out.println("Redis is not available, skipping set operation");
            return;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            System.out.println("Redis SET: " + key + " = " + value);
        } catch (Exception e) {
            System.err.println("Error setting value in Redis: " + e.getMessage());
        }
    }

    /**
     * Get value by key from Redis
     * 
     * @param key Key
     * @return Value or null if not found
     */
    public static String get(String key) {
        if (!redisAvailable) {
            System.out.println("Redis is not available, returning null");
            return null;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            String value = jedis.get(key);
            System.out.println("Redis GET: " + key + " = " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Error getting value from Redis: " + e.getMessage());
            return null;
        }
    }

    /**
     * Set key-value pair with expiration time
     * 
     * @param key     Key
     * @param value   Value
     * @param seconds Expiration time in seconds
     */
    public static void setWithExpiry(String key, String value, int seconds) {
        if (!redisAvailable) {
            System.out.println("Redis is not available, skipping setex operation");
            return;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, seconds, value);
            System.out.println("Redis SETEX: " + key + " = " + value + " (expires in " + seconds + "s)");
        } catch (Exception e) {
            System.err.println("Error setting value with expiry in Redis: " + e.getMessage());
        }
    }

    /**
     * Delete key from Redis
     * 
     * @param key Key to delete
     */
    public static void delete(String key) {
        if (!redisAvailable) {
            System.out.println("Redis is not available, skipping delete operation");
            return;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
            System.out.println("Redis DEL: " + key);
        } catch (Exception e) {
            System.err.println("Error deleting key from Redis: " + e.getMessage());
        }
    }

    /**
     * Check if key exists in Redis
     * 
     * @param key Key to check
     * @return true if key exists, false otherwise
     */
    public static boolean exists(String key) {
        if (!redisAvailable) {
            return false;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        } catch (Exception e) {
            System.err.println("Error checking key existence in Redis: " + e.getMessage());
            return false;
        }
    }

    /**
     * Store hash map in Redis
     * 
     * @param key  Hash key
     * @param hash Map to store
     */
    public static void setHash(String key, Map<String, String> hash) {
        if (!redisAvailable) {
            System.out.println("Redis is not available, skipping hset operation");
            return;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, hash);
            System.out.println("Redis HSET: " + key + " with " + hash.size() + " fields");
        } catch (Exception e) {
            System.err.println("Error setting hash in Redis: " + e.getMessage());
        }
    }

    /**
     * Get hash map from Redis
     * 
     * @param key Hash key
     * @return Map of hash fields and values
     */
    public static Map<String, String> getHash(String key) {
        if (!redisAvailable) {
            System.out.println("Redis is not available, returning null");
            return null;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            Map<String, String> hash = jedis.hgetAll(key);
            System.out.println("Redis HGETALL: " + key + " returned " + hash.size() + " fields");
            return hash;
        } catch (Exception e) {
            System.err.println("Error getting hash from Redis: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get hash field value
     * 
     * @param key   Hash key
     * @param field Field name
     * @return Field value or null
     */
    public static String getHashField(String key, String field) {
        if (!redisAvailable) {
            return null;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(key, field);
        } catch (Exception e) {
            System.err.println("Error getting hash field from Redis: " + e.getMessage());
            return null;
        }
    }

    /**
     * Store session token in Redis
     * 
     * @param sessionId     Session ID
     * @param token         Session token
     * @param expirySeconds Expiration time in seconds
     */
    public static void storeSessionToken(String sessionId, String token, int expirySeconds) {
        setWithExpiry("session:" + sessionId, token, expirySeconds);
    }

    /**
     * Get session token from Redis
     * 
     * @param sessionId Session ID
     * @return Session token or null
     */
    public static String getSessionToken(String sessionId) {
        return get("session:" + sessionId);
    }

    /**
     * Clear all keys in Redis (use with caution!)
     */
    public static void flushAll() {
        if (!redisAvailable) {
            System.out.println("Redis is not available, skipping flush operation");
            return;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushAll();
            System.out.println("Redis FLUSHALL executed");
        } catch (Exception e) {
            System.err.println("Error flushing Redis: " + e.getMessage());
        }
    }

    /**
     * Get all keys matching pattern
     * 
     * @param pattern Key pattern (e.g., "user:*")
     * @return Set of matching keys
     */
    public static Set<String> getKeys(String pattern) {
        if (!redisAvailable) {
            return null;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(pattern);
        } catch (Exception e) {
            System.err.println("Error getting keys from Redis: " + e.getMessage());
            return null;
        }
    }
}
