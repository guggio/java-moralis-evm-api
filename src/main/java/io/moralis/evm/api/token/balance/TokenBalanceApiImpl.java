package io.moralis.evm.api.token.balance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.exception.ApiErrorMessage;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.api.queryparams.QueryParamsManager;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TokenBalance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TokenBalanceApiImpl extends BaseApiImpl implements TokenBalanceApi {

  private final Address address;
  private final QueryParamsManager queryParamsManager;

  public TokenBalanceApiImpl(BaseApi wrappedApi, Address address) {
    super(wrappedApi);
    this.address = address;
    queryParamsManager = new QueryParamsManager();
  }

  @Override
  protected String getUrlPath() {
    return "/" + address.getAddress() + "/erc20" + queryParamsManager.getParams();
  }

  @Override
  public List<TokenBalance> get() {
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
      TypeToken<List<TokenBalance>> token = new TypeToken<>() {};
      return new Gson().fromJson(response.body(), token.getType());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public TokenBalanceApi chain(Chain chain) {
    queryParamsManager.add(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenBalanceApi toBlock(long blockNumber) {
    queryParamsManager.add(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  public TokenBalanceApi tokenAddress(List<Address> tokenAddresses) {
    queryParamsManager.add(QueryParams.tokenAddresses(tokenAddresses));
    return this;
  }
}
