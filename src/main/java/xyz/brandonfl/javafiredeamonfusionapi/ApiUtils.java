package xyz.brandonfl.javafiredeamonfusionapi;

final class ApiUtils {

  private ApiUtils() {
  }

  protected static String cleanApiUrl(String url) {
    return url.replaceFirst("/*$", "");
  }
}
