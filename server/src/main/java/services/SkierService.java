package services;

import com.google.gson.Gson;

import models.SkierVertical;
import repository.SkierRepository;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.LiftRide;
import models.LiftRideBody;
import models.ResponseMsg;
import util.Util;

/**
 * The SkierService is a service for the logic of handling Skier APIs, including URL validation,
 * RequestBody parsing, asynchronous messaging writing, and data retrieving from SkierDao
 */
public class SkierService {

  private final static String LIFT_RIDE_POST_SUCCESS_MSG = "lift ride post success";
  private final static String LIFT_RIDE_POST_FAIL_MSG = "lift ride post fail";
  private final static String VERTICALS_BY_DAY_GET_FAIL_MSG = "verticals by day get fail";
  private final static String VERTICALS_BY_RESORT_GET_FAIL_MSG = "verticals by resort get fail";

  private SkierRepository skierRepository;
  private AsynchronousService asynchronousService;


  /**
   * Instantiates SkierService
   *
   * @param asynchronousService the asynchronous service
   */
  public SkierService(AsynchronousService asynchronousService) {
    this.skierRepository = new SkierRepository();
    this.asynchronousService = asynchronousService;
  }

  /**
   * Handle lift ride post request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleLiftRidePostRequest(HttpServletRequest request, HttpServletResponse response) {
    LiftRide liftRide = buildPostLiftRideBody(request);
    if (liftRide != null) {
      boolean sent = asynchronousService.sendLiftRideToAsynQueueChannel(liftRide);
      if (sent) {
        Util.response201(response, new Gson().toJson(new ResponseMsg(LIFT_RIDE_POST_SUCCESS_MSG)));
        return;
      }
    }
    Util.response400(response, new Gson().toJson(new ResponseMsg(LIFT_RIDE_POST_FAIL_MSG)));
  }

  /**
   * Handle vertical by day get request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleVerticalByDayGetRequest(HttpServletRequest request,
      HttpServletResponse response) {
    String urlPath = request.getPathInfo();
    String[] urlPaths = urlPath.split("/");
    String resortID = urlPaths[1];
    String seasonID = urlPaths[3];
    String dayID = urlPaths[5];
    String skierID = urlPaths[7];
    int verticalCnt = skierRepository.selectVerticalsByDay(resortID, seasonID, dayID, skierID);
    Util.response200(response, String.valueOf(verticalCnt));
  }

  /**
   * Handle vertical by resort get request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleVerticalByResortGetRequest(HttpServletRequest request,
      HttpServletResponse response) {
    if (isValidGetVerticalByResortParas(request)) {
      String urlPath = request.getPathInfo();
      String[] urlPaths = urlPath.split("/");

      String skierID = urlPaths[1];
      String resortID = request.getParameter("resort");
      String seasonID = request.getParameter("season");
      if (seasonID == null || seasonID.isEmpty()) {
        seasonID = "*";
      }
      int verticalCnt = skierRepository.selectVerticals(resortID, seasonID, skierID);
      Util.response200(response, new Gson().toJson(new SkierVertical(seasonID, verticalCnt)));
      return;
    }
    Util.response400(response,
        new Gson().toJson(new ResponseMsg(VERTICALS_BY_RESORT_GET_FAIL_MSG)));
  }

  /**
   * to validate urlPath = /{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
   *
   * @param request the request
   * @return return true if the urlPath is valid, otherwise return false
   */
  public boolean isValidPostLiftRideUrl(HttpServletRequest request) {
    String urlPath = request.getPathInfo();
    String regex = "\\/[0-9]+\\/seasons\\/[0-9]+\\/days\\/[0-9]+\\/skiers\\/[0-9]+";
    return urlPath.matches(regex);
  }

  /**
   * to validate urlPath = /{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
   *
   * @param request the request
   * @return return true if the urlPath is valid, otherwise return false
   */
  public boolean isValidGetVerticalByDayUrl(HttpServletRequest request) {
    String urlPath = request.getPathInfo();
    String regex = "\\/[0-9]+\\/seasons\\/[0-9]+\\/days\\/[0-9]+\\/skiers\\/[0-9]+";
    return urlPath.matches(regex);
  }

  /**
   * to validate urlPath = /{skierID}/vertical
   *
   * @param request the request
   * @return return true if the urlPath is valid, otherwise return false
   */
  public boolean isValidGetVerticalByResortUrl(HttpServletRequest request) {
    String urlPath = request.getPathInfo();
    String regex = "\\/[0-9]+\\/vertical";
    return urlPath.matches(regex);
  }

  /**
   * to validate the paras of the /{skierID}/vertical api calls
   *
   * @param request
   * @return
   */
  private boolean isValidGetVerticalByResortParas(HttpServletRequest request) {
    String resort = request.getParameter("resort");
    if (resort == null) {
      return false;
    }
    return true;
  }

  private LiftRide buildPostLiftRideBody(HttpServletRequest request) {
    LiftRideBody liftRideBody = null;
    StringBuilder sb = new StringBuilder();
    String s = null;
    try {
      while ((s = request.getReader().readLine()) != null) {
        sb.append(s);
      }
      if (sb.toString() == null) {
        return null;
      }
      liftRideBody = new Gson().fromJson(sb.toString(), LiftRideBody.class);
      if (liftRideBody == null || liftRideBody.getTime() == null || liftRideBody.getLiftID() == null
          || liftRideBody.getWaitTime() == null) {
        return null;
      }
    } catch (IOException e) {
      return null;
    }
    LiftRide liftRide = new LiftRide();
    liftRide.setLiftRideBody(liftRideBody);
    String urlPath = request.getPathInfo();
    String[] urlPaths = urlPath.split("/");
    liftRide.setResortID(Integer.valueOf(urlPaths[1]));
    liftRide.setSeasonID(Integer.valueOf(urlPaths[3]));
    liftRide.setDayID(Integer.valueOf(urlPaths[5]));
    liftRide.setSkierID(Integer.valueOf(urlPaths[7]));
    return liftRide;
  }
}
