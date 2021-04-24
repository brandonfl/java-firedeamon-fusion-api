package xyz.brandonfl.firedeamon.fusion.api;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.brandonfl.firedeamon.fusion.api.exception.AuthenticationException;

class Session implements AutoCloseable {

  private final String jSessionId;
  private final FiredeamonFusionApi firedeamonFusionApi;

  protected Session(FiredeamonFusionApi firedeamonFusionApi) throws AuthenticationException {
    this.firedeamonFusionApi = firedeamonFusionApi;
    jSessionId = this.login();
  }

  protected String getjSessionId() {
    return jSessionId;
  }

  private String login() throws AuthenticationException {
    try {
      JsonObject connect = new JsonObject();

      connect.addProperty("userName", this.firedeamonFusionApi.username);
      connect.addProperty("password", this.firedeamonFusionApi.password);

      OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
      MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

      RequestBody body = RequestBody.create(mediaType, connect.toString());
      Request request = new Request.Builder()
          .url(firedeamonFusionApi.url + "/auth/login")
          .method("POST", body)
          .addHeader("Content-Type", "application/x-www-form-urlencoded")
          .build();
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        List<String> cookieList = response.headers().values("Set-Cookie");
        return (cookieList.get(0).split(";"))[0];
      } else {
        throw new AuthenticationException();
      }
    } catch (IOException ioException) {
      return null;
    }
  }

  private void logout(String jSessionId) {
    try {
      OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
      RequestBody body = RequestBody.create(JSON, "{}");
      Request request = new Request.Builder()
          .url(this.firedeamonFusionApi.url + "/auth/logout")
          .header("Cookie", jSessionId)
          .method("DELETE", body)
          .build();
      client.newCall(request).execute();
    } catch (Exception ignored) {
    }
  }

  @Override
  public void close() {
    if (this.jSessionId != null) {
      this.logout(this.jSessionId);
    }
  }
}
