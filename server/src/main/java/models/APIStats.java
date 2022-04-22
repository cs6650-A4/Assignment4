package models;

import java.util.List;

public class APIStats {

  private List<API> endpointStats;

  public APIStats(List<API> endpointStats) {
    this.endpointStats = endpointStats;
  }

  public List<API> getEndpointStats() {
    return endpointStats;
  }

  public void setEndpointStats(List<API> endpointStats) {
    this.endpointStats = endpointStats;
  }
}
