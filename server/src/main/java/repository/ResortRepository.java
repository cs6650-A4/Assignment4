package repository;

import java.util.ArrayList;
import models.Resort;
import models.ResortSkiers;
import models.ResortsList;
import models.SeasonsList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import services.RedisDBService;

/**
 * The ResortRepository is to get resort Data from database or other persistence mechanism
 */
public class ResortRepository {

  /**
   * To get number of unique skiers at resort/season/day
   *
   * @param resortID the resort id
   * @param seasonID the season id
   * @param dayID    the day id
   * @return the long
   */
  public long selectUniqueSkiers(String resortID, String seasonID, String dayID) {
    String key = "SKI:RESORT:UNIQUESKIERS:" + resortID + ":" + seasonID + ":" + dayID;
    JedisPool jedisPool = RedisDBService.getINSTANCE().getJedisPool();
    Jedis resource = jedisPool.getResource();
    long scard = resource.scard(key);
    jedisPool.returnResource(resource);
    return scard;
  }

  /**
   * Select all resorts resorts list.
   *
   * @return the resorts list
   */
  public ResortsList selectAllResorts() {
    ResortsList resorts = new ResortsList(new ArrayList<>());
    Resort resort0 = new Resort(1, "whistler");
    Resort resort1 = new Resort(2, "Vail");
    resorts.getResorts().add(resort0);
    resorts.getResorts().add(resort1);
    return resorts;
  }

  /**
   * Select number of skiers resort skiers.
   *
   * @return the resort skiers
   */
  public ResortSkiers selectNumberOfSkiers() {
    ResortSkiers resortSkiers0 = new ResortSkiers("Mission Ridge", 78999);
    return resortSkiers0;
  }

  /**
   * Select seasons by resort seasons list.
   *
   * @return the seasons list
   */
  public SeasonsList selectSeasonsByResort() {
    SeasonsList seasonsList0 = new SeasonsList(new ArrayList<>());
    seasonsList0.getSeasons().add("2018");
    seasonsList0.getSeasons().add("2019");
    return seasonsList0;
  }
}
