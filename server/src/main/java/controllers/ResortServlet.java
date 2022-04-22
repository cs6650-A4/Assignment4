package controllers;

import com.google.gson.Gson;
import models.ResponseMsg;
import services.ResortService;
import util.Util;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * The ResortServlet is to create resort APIS, including getting a list of ski resorts, getting
 * number of unique skiers at resort/season/day, getting a list of seasons for the specified resort
 * and adding a new season for a resort
 */
@WebServlet(name = "controllers.ResortServlet", value = "/controllers.ResortServlet")
public class ResortServlet extends HttpServlet {

  private static final String INVALID_URL_MSG = "invalid url";
  private ResortService resortService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.resortService = new ResortService();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    if (this.resortService.isValidGetResortsUrl(request)) {
      this.resortService.handleResortsGetRequest(request, response);
      return;
    }
    if (this.resortService.isValidGetNumberOfSkiersUrl(request)) {
      this.resortService.handleNumberOfSkiersGetRequest(request, response);
      return;
    }
    if (this.resortService.isValidGetSeasonsByResortUrl(request)) {
      this.resortService.handleSeasonsByResortGetRequest(request, response);
      return;
    }
    Util.response400(response, new Gson().toJson(new ResponseMsg(INVALID_URL_MSG)));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (this.resortService.isValidPostSeasonUrl(request)) {
      this.resortService.handleSeasonPostRequest(request, response);
      return;
    }
    Util.response400(response, new Gson().toJson(new ResponseMsg(INVALID_URL_MSG)));
  }
}
