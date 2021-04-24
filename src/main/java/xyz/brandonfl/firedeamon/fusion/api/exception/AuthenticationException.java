package xyz.brandonfl.firedeamon.fusion.api.exception;

public class AuthenticationException extends Exception {

  public AuthenticationException() {
    super("Credentials supplied are invalid or otherwise fails to authenticate the user to the provided service");
  }
}
