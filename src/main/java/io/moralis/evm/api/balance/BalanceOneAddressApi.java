package io.moralis.evm.api.balance;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.NativeBalance;

public interface BalanceOneAddressApi extends GetApi<NativeBalance> {

  BalanceOneAddressApi chain(Chain chain);

  BalanceOneAddressApi toBlock(long blockNumber);

}
