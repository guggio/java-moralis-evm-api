package io.moralis.evm.api.token.price;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.Exchange;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.TokenPrice;

public class TokenPriceApiImpl extends ExecutingApi implements TokenPriceApi {

  private final ValidatedAddress contract;

  public TokenPriceApiImpl(BaseApi wrappedApi, ValidatedAddress contract) {
    super(wrappedApi);
    this.contract = contract;
  }

  @Override
  public TokenPrice get() {
    return get(TokenPrice.class);
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/erc20/" + contract.getAddress() + "/price";
  }

  @Override
  public TokenPriceApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenPriceApi exchange(Exchange exchange) {
    addQuery(QueryParams.exchange(exchange));
    return this;
  }

  @Override
  public TokenPriceApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }
}
