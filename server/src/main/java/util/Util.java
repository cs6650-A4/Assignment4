package util;

import com.google.gson.Gson;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * The Util is to provide general utils
 */
public class Util {

  /**
   * Response 200.
   *
   * @param response the response
   * @param jsonMsg  the json msg
   */
  public static void response200(HttpServletResponse response, String jsonMsg) {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.print(jsonMsg);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Response 201.
   *
   * @param response the response
   * @param jsonMsg  the json msg
   */
  public static void response201(HttpServletResponse response, String jsonMsg) {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_CREATED);
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.print(jsonMsg);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Response 400.
   *
   * @param response the response
   * @param jsonMsg  the json msg
   */
  public static void response400(HttpServletResponse response, String jsonMsg) {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.print(new Gson().toJson(jsonMsg));
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
