package io.moralis.evm.api;

import io.moralis.evm.api.balance.BalanceApi;
import io.moralis.evm.api.balance.BalanceApiImpl;
import io.moralis.evm.api.block.BlockApi;
import io.moralis.evm.api.block.BlockApiImpl;
import io.moralis.evm.api.token.TokenApi;
import io.moralis.evm.api.token.TokenApiImpl;
import io.moralis.evm.api.transaction.TransactionApi;
import io.moralis.evm.api.transaction.TransactionApiImpl;

class MoralisApiV2Impl implements MoralisApi, BaseApi {

  private final String apiKey;

  MoralisApiV2Impl(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  public String buildUrl() {
    return "https://deep-index.moralis.io/api/v2";
  }

  @Override
  public String getApiKey() {
    return apiKey;
  }

  @Override
  public TokenApi token() {
    return new TokenApiImpl(this);
  }

  @Override
  public BlockApi block() {
    return new BlockApiImpl(this);
  }

  @Override
  public BalanceApi balance() {
    return new BalanceApiImpl(this);
  }

  @Override
  public TransactionApi transaction() {
    return new TransactionApiImpl(this);
  }
}
