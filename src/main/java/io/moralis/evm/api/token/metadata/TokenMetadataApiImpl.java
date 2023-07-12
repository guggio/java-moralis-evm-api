package io.moralis.evm.api.token.metadata;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.core.ValidatedAddress;

import java.util.List;

public class TokenMetadataApiImpl extends BaseApiImpl implements TokenMetadataApi {

  public TokenMetadataApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  protected String getUrlPath() {
    return "/erc20/metadata";
  }

  @Override
  public TokenMetadataContractsApi contracts(List<ValidatedAddress> validatedAddresses) {
    return new TokenMetadataContractsApiImpl(this, validatedAddresses);
  }

  @Override
  public TokenMetadataSymbolsApi symbols(List<String> symbols) {
    return new TokenMetadataSymbolsApiImpl(this, symbols);
  }
}
