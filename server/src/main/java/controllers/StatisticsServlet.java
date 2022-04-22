package controllers;

import com.google.gson.Gson;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import models.API;
import models.APIStats;
import services.StatisticsService;
import util.Util;

/**
 * The StatisticsServlet is to create statistics APIs
 */
@WebServlet(name = "controllers.StatisticsServlet", value = "/controllers.StatisticsServlet")
public class StatisticsServlet extends HttpServlet {

  private StatisticsService statisticsService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.statisticsService = new StatisticsService();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    statisticsService.handleStatisticsGetRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }
}
