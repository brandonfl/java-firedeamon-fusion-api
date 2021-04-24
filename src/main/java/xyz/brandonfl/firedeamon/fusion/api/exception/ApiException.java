package xyz.brandonfl.firedeamon.fusion.api.exception;

import okhttp3.Response;

public class ApiException extends Exception {

  public ApiException(String message) {
    super(message);
  }

  public ApiException(Response response) {
    super(response.message());
  }

  public ApiException(Exception exception) {
    super(exception.getMessage());
  }
}
