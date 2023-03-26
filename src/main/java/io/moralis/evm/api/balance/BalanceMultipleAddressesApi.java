package io.moralis.evm.api.balance;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.NativeBalances;

import java.util.List;

public interface BalanceMultipleAddressesApi extends GetApi<List<NativeBalances>> {

  BalanceMultipleAddressesApi chain(Chain chain);

  BalanceMultipleAddressesApi toBlock(long blockNumber);

}
