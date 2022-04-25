package services;

import redis.clients.jedis.JedisPool;

public class RedisDBService {

  public static final String REDIS_HOST = "35.88.0.185";
  //  public static final String REDIS_HOST = "localhost";
  public static final int REDIS_PORT = 6379;
  public static final String REDIS_PASSWORD = "123456";

  private JedisPool jedisPool;

  private volatile static RedisDBService INSTANCE = null;

  private RedisDBService() {
    this.jedisPool = new JedisPool(REDIS_HOST, REDIS_PORT, null, REDIS_PASSWORD);
  }

  public static RedisDBService getINSTANCE() {
    if (INSTANCE == null) {
      synchronized (ResortService.class) {
        if (INSTANCE == null) {
          INSTANCE = new RedisDBService();
        }
      }
    }
    return INSTANCE;
  }

  public JedisPool getJedisPool() {
    return jedisPool;
  }
}
