package io.moralis.evm.api.token.balance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.TokenBalance;

import java.util.Arrays;
import java.util.List;

public class TokenBalanceApiImpl extends ExecutingApi implements TokenBalanceApi {

  private final ValidatedAddress validatedAddress;

  public TokenBalanceApiImpl(BaseApi wrappedApi, ValidatedAddress validatedAddress) {
    super(wrappedApi);
    this.validatedAddress = validatedAddress;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/" + validatedAddress.getAddress() + "/erc20";
  }

  @Override
  public List<TokenBalance> get() {
    return Arrays.asList(get(TokenBalance[].class));
  }

  @Override
  public TokenBalanceApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenBalanceApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  public TokenBalanceApi tokenAddress(List<ValidatedAddress> tokenAddresses) {
    addQuery(QueryParams.tokenAddresses(tokenAddresses));
    return this;
  }
}
