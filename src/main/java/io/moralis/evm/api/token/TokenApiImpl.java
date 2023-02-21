package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.token.balance.TokenBalanceApi;
import io.moralis.evm.api.token.balance.TokenBalanceApiImpl;
import io.moralis.evm.core.Address;

public class TokenApiImpl extends BaseApiImpl implements TokenApi {

  public TokenApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }

  @Override
  public TokenBalanceApi balance(Address address) {
    return new TokenBalanceApiImpl(this, address);
  }
}
