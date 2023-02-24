package io.moralis.evm.api.token.price;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.Exchange;
import io.moralis.evm.model.TokenPrice;

public interface TokenPriceApi extends GetApi<TokenPrice> {

  TokenPriceApi chain(Chain chain);

  TokenPriceApi exchange(Exchange exchange);

  TokenPriceApi toBlock(long blockNumber);
}
