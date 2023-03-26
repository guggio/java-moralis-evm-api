package io.moralis.evm.api.balance;

import io.moralis.evm.core.Address;

public interface BalanceApi {

  BalanceOneAddressApi address(Address address);
}
