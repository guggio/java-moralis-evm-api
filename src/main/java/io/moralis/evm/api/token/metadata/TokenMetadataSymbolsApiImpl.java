package io.moralis.evm.api.token.metadata;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20Metadata;

import java.util.Arrays;
import java.util.List;

public class TokenMetadataSymbolsApiImpl extends ExecutingApi implements TokenMetadataSymbolsApi {

  TokenMetadataSymbolsApiImpl(BaseApi wrappedApi, List<String> symbols) {
    super(wrappedApi);
    addSymbolsAsQueryParams(symbols);
  }

  private void addSymbolsAsQueryParams(List<String> symbols) {
    if (symbols.isEmpty()) {
      throw new IllegalArgumentException("Symbols for the TokenMetadataApi must not be empty!");
    }
    addQuery(QueryParams.symbols(symbols));
  }

  @Override
  public TokenMetadataSymbolsApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/symbols";
  }

  @Override
  public List<Erc20Metadata> get() {
    return Arrays.asList(get(Erc20Metadata[].class));
  }
}
