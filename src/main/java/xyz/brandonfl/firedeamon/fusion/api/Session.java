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
