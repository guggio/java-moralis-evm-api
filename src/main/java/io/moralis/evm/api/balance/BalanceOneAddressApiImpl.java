package io.moralis.evm.api.balance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.NativeBalance;

class BalanceOneAddressApiImpl extends ExecutingApi implements BalanceOneAddressApi {

  private final ValidatedAddress validatedAddress;

  BalanceOneAddressApiImpl(BaseApi wrappedApi, ValidatedAddress validatedAddress) {
    super(wrappedApi);
    this.validatedAddress = validatedAddress;
  }

  @Override
  public NativeBalance get() {
    return get(NativeBalance.class);
  }

  @Override
  public BalanceOneAddressApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public BalanceOneAddressApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return String.format("/%s/balance", validatedAddress.getAddress());
  }
}
