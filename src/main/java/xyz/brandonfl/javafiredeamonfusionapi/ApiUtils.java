package xyz.brandonfl.javafiredeamonfusionapi;

import org.jetbrains.annotations.NotNull;

final class ApiUtils {

  private ApiUtils() {
  }

  protected static String cleanApiUrl(@NotNull String url) {
    return url.replaceFirst("/*$", "");
  }
}
