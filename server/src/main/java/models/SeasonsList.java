package models;

import java.util.List;

public class SeasonsList {

  private List<String> seasons;

  public SeasonsList(List<String> seasons) {
    this.seasons = seasons;
  }

  public List<String> getSeasons() {
    return seasons;
  }

  public void setSeasons(List<String> seasons) {
    this.seasons = seasons;
  }
}
