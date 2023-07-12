package io.moralis.evm.api.balance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.core.ValidatedAddress;

import java.util.List;

public class BalanceApiImpl extends BaseApiImpl implements BalanceApi {

  public BalanceApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  public BalanceOneAddressApi address(ValidatedAddress validatedAddress) {
    return new BalanceOneAddressApiImpl(this, validatedAddress);
  }

  @Override
  public BalanceMultipleAddressesApi addresses(List<ValidatedAddress> validatedAddresses) {
    return new BalanceMultipleAddressesApiImpl(this, validatedAddresses);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }
}
