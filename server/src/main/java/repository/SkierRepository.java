package repository;

import java.util.ArrayList;
import java.util.Set;
import models.SkierVertical;
import models.SkierVerticals;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import services.RedisDBService;

/**
 * The SkierRepository is to get skier Data from database or other persistence mechanism
 */
public class SkierRepository {

  /**
   * To get the total vertical for the skier for the specified ski day
   *
   * @param resortID the resort id
   * @param seasonID the season id
   * @param dayID    the day id
   * @param skierID  the skier id
   * @return the int
   */
  public int selectVerticalsByDay(String resortID, String seasonID, String dayID, String skierID) {
    String key =
        "SKI:SKIER:TOTALDAYVERTICALS:" + resortID + ":" + seasonID + ":" + dayID + ":" + skierID;
    JedisPool jedisPool = RedisDBService.getINSTANCE().getJedisPool();
    Jedis resource = jedisPool.getResource();
    String s = resource.get(key);
    if (s == null || s.isEmpty()) {
      return 0;
    }
    int verticals = Integer.parseInt(s);
    jedisPool.returnResource(resource);
    return verticals;
  }

  /**
   * To get the total vertical for the skier for specified seasons/resort
   *
   * @param resortID the resort id
   * @param seasonID the season id
   * @param skierID  the skier id
   * @return the int
   */
  public int selectVerticals(String resortID, String seasonID, String skierID) {
    String key = "SKI:SKIER:TOTALVERTICALS:" + resortID + ":" + seasonID + ":" + skierID;
    JedisPool jedisPool = RedisDBService.getINSTANCE().getJedisPool();
    Jedis resource = jedisPool.getResource();
    int verticals = 0;
    if (seasonID.equals("*")) {
      Set<String> keys = resource.keys(key);
      if (keys == null) {
        return 0;
      }
      for (String k : keys) {
        verticals += Integer.parseInt(resource.get(k));
      }
    } else {
      String s = resource.get(key);
      if (s == null || s.isEmpty()) {
        return 0;
      }
      verticals = Integer.parseInt(s);
    }
    jedisPool.returnResource(resource);
    return verticals;
  }

  /**
   * Select verticals by resort id skier verticals.
   *
   * @param resortId the resort id
   * @return the skier verticals
   */
  public SkierVerticals selectVerticalsByResortId(int resortId) {
    if (resortId == 100) {
      SkierVerticals skierVerticals = new SkierVerticals(new ArrayList<>());
      SkierVertical skierVertical0 = new SkierVertical("2017", 12345677);
      SkierVertical skierVertical1 = new SkierVertical("2018", 787888);
      skierVerticals.getVerticals().add(skierVertical0);
      skierVerticals.getVerticals().add(skierVertical1);
      return skierVerticals;
    }
    return null;
  }
}
