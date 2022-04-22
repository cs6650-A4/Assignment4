package part1.api;

import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import org.apache.commons.httpclient.HttpStatus;

/**
 * The SwaggerSkiersApi that implements ISkiersApi to sent skiers api request to server. The
 * SwaggerSkiersApi is implemented by using the Swagger, which contains thread-safe methods for
 * calling the server APIs.
 */
public class SwaggerSkiersApi implements ISkiersApi {

  private SkiersApi skiersApi;

  /**
   * Instantiates a new Swagger skiers api.
   *
   * @param skiersApi the skiers api
   */
  public SwaggerSkiersApi(SkiersApi skiersApi) {
    this.skiersApi = skiersApi;
  }

  @Override
  public int writeNewLiftRide(Integer liftID, Integer time, Integer waitTime, Integer resortID,
      String seasonID, String dayID, Integer skierID) {
    LiftRide body = new LiftRide();
    body.setLiftID(liftID);
    body.setTime(time);
    body.setWaitTime(waitTime);
    try {
      return this.skiersApi.writeNewLiftRideWithHttpInfo(body, resortID, seasonID, dayID, skierID)
          .getStatusCode();
    } catch (ApiException e) {
      return HttpStatus.SC_BAD_REQUEST;
    }
  }

  @Override
  public void setBasePath(String ip, String port, String serverName) {
    skiersApi.getApiClient().setBasePath("http://" + ip + ":" + port + "/" + serverName);
  }

  /**
   * Gets skiers api.
   *
   * @return the skiers api
   */
  public SkiersApi getSkiersApi() {
    return skiersApi;
  }

  /**
   * Sets skiers api.
   *
   * @param skiersApi the skiers api
   */
  public void setSkiersApi(SkiersApi skiersApi) {
    this.skiersApi = skiersApi;
  }
}
