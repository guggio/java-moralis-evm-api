package io.moralis.evm.api.token.metadata;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.core.Address;

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
  public TokenMetadataContractsApi contracts(List<Address> addresses) {
    return new TokenMetadataContractsApiImpl(this, addresses);
  }
}
