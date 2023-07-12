package io.moralis.evm.api.token.metadata;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.Erc20Metadata;

import java.util.Arrays;
import java.util.List;

public class TokenMetadataContractsApiImpl extends ExecutingApi implements TokenMetadataContractsApi {

  public TokenMetadataContractsApiImpl(BaseApi wrappedApi, List<ValidatedAddress> validatedAddresses) {
    super(wrappedApi);
    addAddressesAsQueryParam(validatedAddresses);
  }

  private void addAddressesAsQueryParam(List<ValidatedAddress> validatedAddresses) {
    if (validatedAddresses.isEmpty()) {
      throw new IllegalArgumentException("Addresses for the TokenMetadataApi must not be empty!");
    }
    addQuery(QueryParams.addresses(validatedAddresses)); // required query param
  }

  @Override
  public List<Erc20Metadata> get() {
    return Arrays.asList(get(Erc20Metadata[].class));
  }

  @Override
  public TokenMetadataContractsApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "";
  }
}
