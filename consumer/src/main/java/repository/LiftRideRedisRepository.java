package repository;

import com.google.gson.Gson;
import java.util.UUID;
import model.LiftRide;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class LiftRideRedisRepository implements IGenericRepository<LiftRide> {

  private static final String REDIS_ADDRESS = "localhost";
  private static final int REDIS_PORT = 6379;
  private JedisPool jedisPool = null;

  public LiftRideRedisRepository() {
    jedisPool = new JedisPool(REDIS_ADDRESS, REDIS_PORT);
  }

  @Override
  public void add(LiftRide item) {
    String id = UUID.randomUUID().toString();
    Jedis jedis = jedisPool.getResource();
    String key = "resort:" + item.getResortID() + ":season:" + item.getSeasonID() + ":day:" + item.getDayID()
        + ":skier:" + item.getSkierID() + ":liftRide";
    jedis.hsetnx(key, id, new Gson().toJson(item.getLiftRideBody()));
    jedisPool.returnResource(jedis);
  }
}
