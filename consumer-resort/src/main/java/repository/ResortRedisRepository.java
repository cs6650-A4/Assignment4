package repository;

import model.LiftRide;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ResortRedisRepository implements IGenericRepository<LiftRide> {

  private JedisPool jedisPool = null;

  public ResortRedisRepository(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  @Override
  public void addDataForUniqueSkiers(LiftRide item) {
    Jedis jedis = jedisPool.getResource();
    String key = "SKI:RESORT:UNIQUESKIERS:" + item.getResortID() + ":" + item.getSeasonID() + ":" + item.getDayID();
    String value = String.valueOf(item.getSkierID());
    jedis.sadd(key, value);
    jedisPool.returnResource(jedis);
  }

  @Override
  public void addDataForLiftRidesOnDayAndLift(LiftRide item) {
    Jedis jedis = jedisPool.getResource();
    String key =
        "SKI:RESORT:RIDESONDAYLIFT:" + item.getDayID() + ":" + item.getLiftRideBody().getLiftID();
    jedis.sadd(key, item.getLiftRideBody().getId());
    jedisPool.returnResource(jedis);
  }

  @Override
  public void addDataForLiftRidesOnDayAndHour(LiftRide item) {
    Jedis jedis = jedisPool.getResource();
    String key = "SKI:RESORT:RIDESONDAYHOUR:" + item.getDayID() + ":" + (int) (
        item.getLiftRideBody().getTime() / 60);
    jedis.sadd(key, item.getLiftRideBody().getId());
    jedisPool.returnResource(jedis);
  }

//  @Override
//  public void addDataForLiftRidesOnDayAndLift(LiftRide item) {
//    Jedis jedis = jedisPool.getResource();
//    String key =
//        "SKI:RESORT:RIDESONDAYLIFT:" + item.getDayID() + ":" + item.getLiftRideBody().getLiftID();
//    String old_rides = jedis.get(key);
//    int new_rides = 0;
//    if (old_rides != null) {
//      new_rides += Integer.valueOf(old_rides);
//    }
//    new_rides += 1;
//    String value = String.valueOf(new_rides);
//    jedis.set(key, value);
//    jedisPool.returnResource(jedis);
//  }
//  @Override
//  public void addDataForLiftRidesOnDayAndHour(LiftRide item) {
//    Jedis jedis = jedisPool.getResource();
//    String key = "SKI:RESORT:RIDESONDAYHOUR:" + item.getDayID() + ":" + (int) (
//        item.getLiftRideBody().getTime() / 60);
//    String old_rides = jedis.get(key);
//    int new_rides = 0;
//    if (old_rides != null) {
//      new_rides += Integer.valueOf(old_rides);
//    }
//    new_rides += 1;
//    String value = String.valueOf(new_rides);
//    jedis.set(key, value);
//    jedisPool.returnResource(jedis);
//  }
}
