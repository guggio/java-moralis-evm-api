package io.moralis.evm.api.balance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.core.Address;

public class BalanceApiImpl extends BaseApiImpl implements BalanceApi {

  public BalanceApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  public BalanceOneAddressApi address(Address address) {
    return new BalanceOneAddressApiImpl(this, address);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }
}
