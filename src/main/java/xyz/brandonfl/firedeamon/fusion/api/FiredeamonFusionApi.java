package xyz.brandonfl.firedeamon.fusion.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.brandonfl.firedeamon.fusion.api.DTO.ServiceAction;
import xyz.brandonfl.firedeamon.fusion.api.DTO.Services;
import xyz.brandonfl.firedeamon.fusion.api.DTO.ServiceInformation;

public class FiredeamonFusionApi {

  private final String url;
  private final String username;
  private final String password;

  public FiredeamonFusionApi(String url, String username, String password) {
    this.url = ApiUtils.cleanApiUrl(url);
    this.username = username;
    this.password = password;
  }
  
  public String login() {
    try {
      JsonObject connect = new JsonObject();

      connect.addProperty("userName", username);
      connect.addProperty("password", password);

      OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
      MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

      RequestBody body = RequestBody.create(mediaType, connect.toString());
      Request request = new Request.Builder()
          .url(url + "/auth/login")
          .method("POST", body)
          .addHeader("Content-Type", "application/x-www-form-urlencoded")
          .build();
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        List<String> cookieList = response.headers().values("Set-Cookie");
        return (cookieList.get(0).split(";"))[0];
      } else {
        return null;
      }
    } catch (IOException ioException) {
      return null;
    }
  }

  public void logout(String jSessionId) {
    try {
      OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
      RequestBody body = RequestBody.create(JSON, "{}");
      Request request = new Request.Builder()
          .url(url + "/auth/logout")
          .header("Cookie", jSessionId)
          .method("DELETE", body)
          .build();
      client.newCall(request).execute();
    } catch (Exception ignored) {
    }
  }


  public List<ServiceInformation> getServices() {
    String jSessionId = login();
    System.out.println(jSessionId);
    if (jSessionId != null) {
      try {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url(url + "/api/v1/services.datatables?fd-only")
            .header("Cookie", jSessionId)
            .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();

        return new Gson().fromJson(response.body().string(), Services.class).data;
      } catch (Exception exception) {
      }

      logout(jSessionId);
    }

    return new ArrayList<>();
  }

  public ServiceInformation getService(String serviceName) {
    return getServices().stream()
        .filter(serviceInformation ->
            serviceInformation.service != null
                && serviceInformation.service.name != null
                && serviceInformation.service.name.equals(serviceName))
        .findFirst().orElse(null);
  }

  public void updateServiceStatus(String serviceName, ServiceAction serviceAction) {
    String jSessionId = login();
    if (jSessionId != null) {
      try {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType,
            MessageFormat.format("svcname={0}&action={1}", serviceName, serviceAction.getToken()));

        System.out.println(body.toString());
        Request request = new Request.Builder()
            .url(url + "/api/v1/services.control")
            .header("Cookie", jSessionId)
            .method("POST", body)
            .build();
        Response response = client.newCall(request).execute();

        System.out.println(response.code());
      } catch (Exception exception) {
      }

      logout(jSessionId);
    }
  }
}