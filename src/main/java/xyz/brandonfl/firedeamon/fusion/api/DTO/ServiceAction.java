package xyz.brandonfl.firedeamon.fusion.api.DTO;

public enum ServiceAction {
  START("start"),
  RESTART("restart"),
  STOP("stop");

  private final String token;

  public String getToken() {
    return token;
  }

  ServiceAction(String token) {
    this.token = token;
  }
}
