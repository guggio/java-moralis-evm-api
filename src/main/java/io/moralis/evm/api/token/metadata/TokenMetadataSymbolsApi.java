package io.moralis.evm.api.token.metadata;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20Metadata;

import java.util.List;

public interface TokenMetadataSymbolsApi extends GetApi<List<Erc20Metadata>> {

  TokenMetadataSymbolsApi chain(Chain chain);
}
