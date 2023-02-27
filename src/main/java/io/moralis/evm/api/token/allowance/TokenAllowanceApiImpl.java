package io.moralis.evm.api.token.allowance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TokenAllowance;

public class TokenAllowanceApiImpl extends ExecutingApi implements TokenAllowanceApi {

  private final Address contract;

  public TokenAllowanceApiImpl(BaseApi wrappedApi, Address contract, Address owner, Address spender) {
    super(wrappedApi);
    this.contract = contract;
    // adding the required parameters
    addQuery(QueryParams.ownerAddress(owner));
    addQuery(QueryParams.spenderAddress(spender));
  }

  @Override
  public TokenAllowanceApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenAllowance get() {
    return get(TokenAllowance.class);
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/erc20/" + contract.getAddress() + "/allowance";
  }
}
