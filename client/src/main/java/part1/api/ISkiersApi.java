package part1.api;

import io.swagger.client.ApiException;

/**
 * The interface ISkiersApi is to call skiers api request to server.
 */
public interface ISkiersApi {

  /**
   * Upload a new lift ride post request to server
   *
   * @param liftID   the lift id
   * @param time     the time
   * @param waitTime the wait time
   * @param resortID the resort id
   * @param seasonID the season id
   * @param dayID    the day id
   * @param skierID  the skier id
   * @return the statusCode
   * @throws ApiException the api exception
   */
  int writeNewLiftRide(Integer liftID, Integer time, Integer waitTime, Integer resortID,
      String seasonID, String dayID,
      Integer skierID);

  /**
   * Sets base path.
   *
   * @param ip         the ip
   * @param port       the port
   * @param serverName the server name
   */
  void setBasePath(String ip, String port, String serverName);
}
