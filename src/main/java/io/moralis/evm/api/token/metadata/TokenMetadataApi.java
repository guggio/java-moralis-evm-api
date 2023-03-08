package io.moralis.evm.api.token.metadata;

import io.moralis.evm.core.Address;

import java.util.List;

public interface TokenMetadataApi {

  TokenMetadataContractsApi contracts(List<Address> contract);

}
