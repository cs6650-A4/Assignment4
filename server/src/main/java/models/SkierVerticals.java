package models;

import java.util.List;

public class SkierVerticals {

  private List<SkierVertical> verticals;

  public SkierVerticals(List<SkierVertical> verticals) {
    this.verticals = verticals;
  }

  public List<SkierVertical> getVerticals() {
    return verticals;
  }

  public void setVerticals(List<SkierVertical> verticals) {
    this.verticals = verticals;
  }
}
