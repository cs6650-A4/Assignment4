package models;

public class LiftRide {

  private Integer resortID;
  private Integer seasonID;
  private Integer dayID;
  private Integer skierID;
  private LiftRideBody liftRideBody;

  public LiftRide() {
  }

  public LiftRide(Integer resortID, Integer seasonID, Integer dayID, Integer skierID,
      LiftRideBody liftRideBody) {
    this.resortID = resortID;
    this.seasonID = seasonID;
    this.dayID = dayID;
    this.skierID = skierID;
    this.liftRideBody = liftRideBody;
  }

  public Integer getResortID() {
    return resortID;
  }

  public void setResortID(Integer resortID) {
    this.resortID = resortID;
  }

  public Integer getSeasonID() {
    return seasonID;
  }

  public void setSeasonID(Integer seasonID) {
    this.seasonID = seasonID;
  }

  public Integer getDayID() {
    return dayID;
  }

  public void setDayID(Integer dayID) {
    this.dayID = dayID;
  }

  public Integer getSkierID() {
    return skierID;
  }

  public void setSkierID(Integer skierID) {
    this.skierID = skierID;
  }

  public LiftRideBody getLiftRideBody() {
    return liftRideBody;
  }

  public void setLiftRideBody(LiftRideBody liftRideBody) {
    this.liftRideBody = liftRideBody;
  }

  @Override
  public String toString() {
    return "LiftRide{" +
        "resortID=" + resortID +
        ", seasonID=" + seasonID +
        ", dayID=" + dayID +
        ", skierID=" + skierID +
        ", liftRideBody=" + liftRideBody +
        '}';
  }
}
