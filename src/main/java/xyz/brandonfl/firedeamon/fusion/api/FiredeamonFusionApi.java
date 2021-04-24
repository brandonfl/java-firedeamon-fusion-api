package xyz.brandonfl.firedeamon.fusion.api;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
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
import xyz.brandonfl.firedeamon.fusion.api.exception.ApiException;
import xyz.brandonfl.firedeamon.fusion.api.exception.AuthenticationException;

public class FiredeamonFusionApi {

  protected final String url;
  protected final String username;
  protected final String password;

  public FiredeamonFusionApi(String url, String username, String password) {
    this.url = ApiUtils.cleanApiUrl(url);
    this.username = username;
    this.password = password;
  }
  
  public List<ServiceInformation> getServices() throws AuthenticationException, ApiException {
    try (Session session = new Session(this)) {
      OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
      Request request = new Request.Builder()
          .url(url + "/api/v1/services.datatables?fd-only")
          .header("Cookie", session.getjSessionId())
          .method("GET", null)
          .build();
      Response response = client.newCall(request).execute();

      if (response.isSuccessful()) {
        return new Gson().fromJson(response.body().string(), Services.class).data;
      } else {
        throw new ApiException(response);
      }
    } catch (IOException ioException) {
      return new ArrayList<>();
    }
  }

  public ServiceInformation getService(String serviceName) throws AuthenticationException, ApiException {
    return getServices().stream()
        .filter(serviceInformation ->
            serviceInformation.service != null
                && serviceInformation.service.name != null
                && serviceInformation.service.name.equals(serviceName))
        .findFirst().orElse(null);
  }

  public void updateServiceStatus(String serviceName, ServiceAction serviceAction) throws AuthenticationException, ApiException {
    try (Session session = new Session(this)) {
      OkHttpClient client = new OkHttpClient().newBuilder()
          .build();

      MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
      RequestBody body = RequestBody.create(mediaType,
          MessageFormat.format("svcname={0}&action={1}", serviceName, serviceAction.getToken()));

      Request request = new Request.Builder()
          .url(url + "/api/v1/services.control")
          .header("Cookie", session.getjSessionId())
          .method("POST", body)
          .build();

      Response response = client.newCall(request).execute();

      System.out.println(response.code());
      System.out.println(response.isSuccessful());

      if (!response.isSuccessful()) {
        if (response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
          throw new ApiException(MessageFormat.format("Service {0} is already in the state {1}", serviceName, serviceAction.getToken()));
        } else {
          throw new ApiException(response);
        }
      }
    } catch (IOException ioException) {
      throw new ApiException(ioException);
    }
  }
}