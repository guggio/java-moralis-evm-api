package io.moralis.evm.api.balance;

import io.moralis.evm.core.ValidatedAddress;

import java.util.List;

public interface BalanceApi {

  BalanceOneAddressApi address(ValidatedAddress validatedAddress);

  BalanceMultipleAddressesApi addresses(List<ValidatedAddress> validatedAddresses);
}
