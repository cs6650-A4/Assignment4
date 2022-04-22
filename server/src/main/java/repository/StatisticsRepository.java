package repository;

import java.util.ArrayList;
import models.API;
import models.APIStats;

/**
 * The StatisticsDao is to get statistics Data from database or other persistence mechanism
 */
public class StatisticsRepository {

  /**
   * Select all statistics api stats.
   *
   * @return the api stats
   */
  public APIStats selectAllStatistics() {
    API api0 = new API("/resorts", "GET", 22, 199);
    API api1 = new API("/resorts", "POST", 12, 89);
    APIStats apiStats = new APIStats(new ArrayList<>());
    apiStats.getEndpointStats().add(api0);
    apiStats.getEndpointStats().add(api1);
    return apiStats;
  }
}
