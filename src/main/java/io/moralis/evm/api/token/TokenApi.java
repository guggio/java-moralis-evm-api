package io.moralis.evm.api.token;

import io.moralis.evm.api.token.allowance.TokenAllowanceApi;
import io.moralis.evm.api.token.balance.TokenBalanceApi;
import io.moralis.evm.api.token.price.TokenPriceApi;
import io.moralis.evm.api.token.transfer.TokenTransferApi;
import io.moralis.evm.core.Address;

public interface TokenApi {

  TokenBalanceApi balance(Address address);

  TokenAllowanceApi allowance(Address contract, Address owner, Address spender);

  TokenPriceApi price(Address contract);

  TokenTransferApi transfer();
}
