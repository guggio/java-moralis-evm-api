package io.moralis.evm.api.balance;

import io.moralis.evm.core.Address;

import java.util.List;

public interface BalanceApi {

  BalanceOneAddressApi address(Address address);

  BalanceMultipleAddressesApi addresses(List<Address> addresses);
}
