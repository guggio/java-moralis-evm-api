package io.moralis.evm.api.token.price;

import com.google.gson.Gson;
import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.exception.ApiErrorMessage;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.api.queryparams.QueryParamsManager;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.Exchange;
import io.moralis.evm.model.TokenPrice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TokenPriceApiImpl extends BaseApiImpl implements TokenPriceApi {

  private final Address contract;
  private final QueryParamsManager queryParamsManager;

  public TokenPriceApiImpl(BaseApi wrappedApi, Address contract) {
    super(wrappedApi);
    this.contract = contract;
    queryParamsManager = new QueryParamsManager();
  }

  @Override
  public TokenPrice get() {
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
      return new Gson().fromJson(response.body(), TokenPrice.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected String getUrlPath() {
    return "/erc20/" + contract.getAddress() + "/price" + queryParamsManager.getParams();
  }

  @Override
  public TokenPriceApi chain(Chain chain) {
    queryParamsManager.add(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenPriceApi exchange(Exchange exchange) {
    queryParamsManager.add(QueryParams.exchange(exchange));
    return this;
  }

  @Override
  public TokenPriceApi toBlock(long blockNumber) {
    queryParamsManager.add(QueryParams.toBlock(blockNumber));
    return this;
  }
}
