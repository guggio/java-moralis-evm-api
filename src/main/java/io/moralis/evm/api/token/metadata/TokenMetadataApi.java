package io.moralis.evm.api.token.metadata;

import io.moralis.evm.core.ValidatedAddress;

import java.util.List;

public interface TokenMetadataApi {

  TokenMetadataContractsApi contracts(List<ValidatedAddress> contract);

  TokenMetadataSymbolsApi symbols(List<String> symbols);

}
