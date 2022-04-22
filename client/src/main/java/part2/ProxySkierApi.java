package part2;

import part1.api.ISkiersApi;
import part1.api.SwaggerSkiersApi;

/**
 * ProxySkierApi acts as a substitute for SkiersApi, providing more additional behaviors of
 * calculating the latency of each post and recording the latency to the Record Object that will be
 * written to csv files after all phases of post requests are completed
 */
public class ProxySkierApi implements ISkiersApi {

  private SwaggerSkiersApi swaggerSkiersApi;
  private Record record;

  /**
   * Instantiates a new ProxySkierApi
   *
   * @param swaggerSkiersApi the swagger skiers api
   * @param record           the record
   */
  public ProxySkierApi(SwaggerSkiersApi swaggerSkiersApi, Record record) {
    this.swaggerSkiersApi = swaggerSkiersApi;
    this.record = record;
  }

  @Override
  public int writeNewLiftRide(Integer liftID, Integer time, Integer waitTime, Integer resortID,
      String seasonID, String dayID, Integer skierID) {
    long startTime = System.currentTimeMillis();
    int statusCode = this.swaggerSkiersApi.writeNewLiftRide(liftID, time, waitTime, resortID,
        seasonID, dayID, skierID);
    long endTime = System.currentTimeMillis();
    long latency = endTime - startTime;
    this.record.getLatencyList().add(latency);
    String[] record = new String[]{String.valueOf(startTime), "POST", String.valueOf(latency),
        String.valueOf(statusCode)};
    this.record.getRecordList().add(record);
    return statusCode;
  }

  @Override
  public void setBasePath(String ip, String port, String serverName) {
    this.swaggerSkiersApi.setBasePath(ip, port, serverName);
  }
}

