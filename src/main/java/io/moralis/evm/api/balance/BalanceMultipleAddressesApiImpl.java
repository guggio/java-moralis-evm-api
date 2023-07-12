package io.moralis.evm.api.balance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.NativeBalances;

import java.util.List;

class BalanceMultipleAddressesApiImpl extends ExecutingApi implements BalanceMultipleAddressesApi {

  BalanceMultipleAddressesApiImpl(BaseApi wrappedApi, List<ValidatedAddress> validatedAddresses) {
    super(wrappedApi);
    addQuery(QueryParams.walletAddresses(validatedAddresses));
  }

  @Override
  public List<NativeBalances> get() {
    return List.of(get(NativeBalances[].class));
  }

  @Override
  public BalanceMultipleAddressesApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public BalanceMultipleAddressesApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/wallets/balances";
  }
}
