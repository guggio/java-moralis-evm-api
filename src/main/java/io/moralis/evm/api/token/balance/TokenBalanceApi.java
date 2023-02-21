package io.moralis.evm.api.token.balance;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TokenBalance;

import java.util.List;

public interface TokenBalanceApi extends GetApi<List<TokenBalance>> {

  TokenBalanceApi chain(Chain chain);

  TokenBalanceApi toBlock(long blockNumber);

  TokenBalanceApi tokenAddress(List<Address> tokenAddresses);

}
