/*
 * MIT License
 *
 * Copyright (c) 2021 Fontany--Legall Brandon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

/**
 * Firedeamon Fusion API for Java.
 *
 * @author brandon fontany-legall
 */
public class FiredeamonFusionApi {

  protected final String url;
  protected final String username;
  protected final String password;

  /**
   * Open a new connection with a FireDaemon Fusion instance
   * @param url url of the FireDaemon Fusion instance. For example https://localhost:20604
   * @param username the name of the FireDaemon user
   * @param password the password for the FireDaemon user
   *
   * @since 6.0.0
   */
  public FiredeamonFusionApi(String url, String username, String password) {
    this.url = ApiUtils.cleanApiUrl(url);
    this.username = username;
    this.password = password;
  }

  /**
   * Fetch FireDaemon services
   * @return List of {@link ServiceInformation}
   * @throws AuthenticationException In case of authentication exception
   * @throws ApiException If the api return an error
   *
   * @since 6.0.0
   */
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

  /**
   * Fetch information of a specific service
   * @param serviceName service name as {@link String}
   * @return {@link ServiceInformation} of the service
   * @throws AuthenticationException In case of authentication exception
   * @throws ApiException If the api return an error
   *
   * @since 6.0.0
   */
  public ServiceInformation getService(String serviceName) throws AuthenticationException, ApiException {
    return getServices().stream()
        .filter(serviceInformation ->
            serviceInformation.getService() != null
                && serviceInformation.getService().getName() != null
                && serviceInformation.getService().getName().equals(serviceName))
        .findFirst().orElse(null);
  }

  /**
   * Update the status of a specific service. We can :
   * <ul>
   *   <li>Start the service with {@link ServiceAction#START}</li>
   *   <li>Restart the service with {@link ServiceAction#RESTART}</li>
   *   <li>Stop the service with {@link ServiceAction#STOP}</li>
   * </ul>
   *
   * @param serviceName service name as {@link String}
   * @param serviceAction the {@link ServiceAction} to perform on the service
   * @throws AuthenticationException In case of authentication exception
   * @throws ApiException If the api return an error
   *
   * @since 6.0.0
   */
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