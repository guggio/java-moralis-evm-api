package io.moralis.evm.api.token.transfer;

import com.google.gson.Gson;
import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.exception.ApiErrorMessage;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.api.queryparams.QueryParamsManager;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20TransactionCollection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class TokenTransferWalletApiImpl extends BaseApiImpl implements TokenTransferWalletApi {

  private final Address wallet;
  private final QueryParamsManager queryParamsManager;

  public TokenTransferWalletApiImpl(BaseApi wrappedApi, Address wallet) {
    super(wrappedApi);
    this.wallet = wallet;
    queryParamsManager = new QueryParamsManager();
  }

  @Override
  protected String getUrlPath() {
    return "/" + wallet.getAddress() + "/erc20/transfers" + getParams();
  }

  private String getParams() {
    return queryParamsManager.getParams();
  }

  @Override
  public TokenTransferWalletApi chain(Chain chain) {
    queryParamsManager.add(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenTransferWalletApi fromBlock(long blockNumber) {
    queryParamsManager.add(QueryParams.fromBlock(blockNumber));
    return this;
  }

  @Override
  public TokenTransferWalletApi toBlock(long blockNumber) {
    queryParamsManager.add(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  public TokenTransferWalletApi fromDate(LocalDateTime fromDate) {
    queryParamsManager.add(QueryParams.fromDate(fromDate));
    return this;
  }

  @Override
  public TokenTransferWalletApi toDate(LocalDateTime toDate) {
    queryParamsManager.add(QueryParams.toDate(toDate));
    return this;
  }

  @Override
  public TokenTransferWalletApi pageSize(int pageSize) {
    queryParamsManager.add(QueryParams.pageSize(pageSize));
    return this;
  }

  @Override
  public TokenTransferWalletApi disableTotal(boolean disableTotal) {
    queryParamsManager.add(QueryParams.disableTotal(disableTotal));
    return this;
  }

  @Override
  public TokenTransferWalletApi cursor(String cursor) {
    queryParamsManager.add(QueryParams.cursor(cursor));
    return this;
  }

  @Override
  public Erc20TransactionCollection get() {
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
      return new Gson().fromJson(response.body(), Erc20TransactionCollection.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
