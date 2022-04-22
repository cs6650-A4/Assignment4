package services;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.APIStats;
import repository.StatisticsRepository;
import util.Util;

/**
 * The StatisticsService is a service for the logic of handling statistics APIs, including URL
 * validation, RequestBody parsing, data calculating, and data retrieving from StatisticsDao
 */
public class StatisticsService {

  private StatisticsRepository statisticsRepository = new StatisticsRepository();

  /**
   * Handle statistics get request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleStatisticsGetRequest(HttpServletRequest request, HttpServletResponse response) {
    APIStats apiStats = statisticsRepository.selectAllStatistics();
    Util.response200(response, new Gson().toJson(apiStats));
  }

}
