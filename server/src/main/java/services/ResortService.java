package services;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ResortSkiers;
import models.ResortsList;
import models.ResponseMsg;
import models.Season;
import models.SkierVertical;
import repository.ResortRepository;
import util.Util;

/**
 * The ResortService is a service for the logic of handling resort APIs, including URL validation,
 * RequestBody parsing, and data retrieving from ResortDao
 */
public class ResortService {

  private final static String SEASON_POST_SUCCESS_MSG = "season post success";
  private final static String SEASON_POST_FAIL_MSG = "season post fail";

  private static final ResortRepository repository = new ResortRepository();

  /**
   * Handle resorts get request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleResortsGetRequest(HttpServletRequest request,
      HttpServletResponse response) {
    ResortsList resortsList = repository.selectAllResorts();
    Util.response200(response, new Gson().toJson(resortsList));
  }

  /**
   * Handle number of skiers get request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleNumberOfSkiersGetRequest(HttpServletRequest request,
      HttpServletResponse response) {
    String urlPath = request.getPathInfo();
    String[] urlPaths = urlPath.split("/");
    String resortID = urlPaths[1];
    String seasonID = urlPaths[3];
    String dayID = urlPaths[5];
    long uniqueSkiersNum = repository.selectUniqueSkiers(resortID, seasonID, dayID);
    Util.response200(response, new Gson().toJson(uniqueSkiersNum));
  }

  /**
   * Handle seasons by resort get request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleSeasonsByResortGetRequest(HttpServletRequest request,
      HttpServletResponse response) {
    ResortsList resortsList = repository.selectAllResorts();
    Util.response200(response, new Gson().toJson(resortsList));
  }


  /**
   * Handle season post request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleSeasonPostRequest(HttpServletRequest request, HttpServletResponse response) {
    if (isValidPostSeasonParameters(request)) {
      Util.response201(response, new Gson().toJson(new ResponseMsg(SEASON_POST_SUCCESS_MSG)));
      return;
    }
    Util.response400(response, new Gson().toJson(new ResponseMsg(SEASON_POST_FAIL_MSG)));
  }

  /**
   * to validate urlPath = "/resorts"
   *
   * @param request the request
   * @return return true if the urlPath is valid, otherwise return false
   */
  public boolean isValidGetResortsUrl(HttpServletRequest request) {
    String urlPath = request.getPathInfo();
    return urlPath == null || urlPath.isEmpty();
  }

  /**
   * to validate urlPath = "/resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers"
   *
   * @param request the request
   * @return return true if the urlPath is valid, otherwise return false
   */
  public boolean isValidGetNumberOfSkiersUrl(HttpServletRequest request) {
    String urlPath = request.getPathInfo();
    String regex = "\\/[0-9]+\\/seasons\\/[0-9]+\\/day\\/[0-9]+\\/skiers";
    return urlPath.matches(regex);
  }

  /**
   * to validate urlPath = /resorts/{resortID}/seasons
   *
   * @param request the request
   * @return return true if the urlPath is valid, otherwise return false
   */
  public boolean isValidGetSeasonsByResortUrl(HttpServletRequest request) {
    String urlPath = request.getPathInfo();
    String regex = "\\/[0-9]+\\/seasons";
    return urlPath.matches(regex);
  }

  /**
   * to validate urlPath = /resorts/{resortID}/seasons/
   *
   * @param request the request
   * @return return true if the urlPath is valid, otherwise return false
   */
  public boolean isValidPostSeasonUrl(HttpServletRequest request) {
    String urlPath = request.getPathInfo();
    String regex = "\\/[0-9]+\\/seasons";
    return urlPath.matches(regex);
  }

  private boolean isValidPostSeasonParameters(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    String s = null;
    try {
      while ((s = request.getReader().readLine()) != null) {
        sb.append(s);
      }
      Season season = new Gson().fromJson(sb.toString(), Season.class);
      if (season.getSeason() == null || season.getSeason().length() != 4) {
        return false;
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

}
