package io.moralis.evm.api;

import com.google.gson.Gson;
import io.moralis.evm.api.exception.ApiErrorMessage;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.api.queryparams.QueryParamsManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class ExecutingApi extends BaseApiImpl {

  private final QueryParamsManager queryParamsManager;

  protected ExecutingApi(BaseApi wrappedApi) {
    super(wrappedApi);
    queryParamsManager = new QueryParamsManager();
  }

  @Override
  protected final String getUrlPath() {
    return getUrlPartBeforeQueryParams() + getQueryParams();
  }

  protected abstract String getUrlPartBeforeQueryParams();

  private String getQueryParams() {
    return queryParamsManager.getParams();
  }

  protected final void addQuery(QueryParams queryParams) {
    queryParamsManager.add(queryParams);
  }

  protected final void removeQuery(QueryParams queryParams) {
    queryParamsManager.remove(queryParams);
  }

  protected final <T> T get(Class<T> clazz) {
    HttpClient httpClient = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(buildUrl()))
        .header("X-API-KEY", getApiKey())
        .GET()
        .build();

    try {
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
        throw new ConnectionException(response.statusCode(), new Gson().fromJson(response.body(), ApiErrorMessage.class));
      }
      return new Gson().fromJson(response.body(), clazz);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
