package io.moralis.evm.api.token.allowance;

import com.google.gson.Gson;
import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.exception.ApiErrorMessage;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.api.queryparams.QueryParamsManager;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TokenAllowance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TokenAllowanceApiImpl extends BaseApiImpl implements TokenAllowanceApi {

  private final Address contract;
  private final QueryParamsManager queryParamsManager;

  public TokenAllowanceApiImpl(BaseApi wrappedApi, Address contract, Address owner, Address spender) {
    super(wrappedApi);
    this.contract = contract;
    queryParamsManager = new QueryParamsManager();
    // adding the required parameters
    queryParamsManager.add(QueryParams.ownerAddress(owner));
    queryParamsManager.add(QueryParams.spenderAddress(spender));
  }

  @Override
  public TokenAllowanceApi chain(Chain chain) {
    queryParamsManager.add(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenAllowance get() {
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
      return new Gson().fromJson(response.body(), TokenAllowance.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected String getUrlPath() {
    return "/erc20/" + contract.getAddress() + "/allowance" + getQueryParams();
  }

  private String getQueryParams() {
    return queryParamsManager.getParams();
  }
}
