package models;

public class SkierVertical {

  private String seasonID;
  private Integer totalVert;

  public SkierVertical(String seasonID, Integer totalVert) {
    this.seasonID = seasonID;
    this.totalVert = totalVert;
  }

  public String getSeasonID() {
    return seasonID;
  }

  public void setSeasonID(String seasonID) {
    this.seasonID = seasonID;
  }

  public Integer getTotalVert() {
    return totalVert;
  }

  public void setTotalVert(Integer totalVert) {
    this.totalVert = totalVert;
  }
}
