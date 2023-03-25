package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.token.metadata.TokenMetadataSymbolsApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20Metadata;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenMetadataSymbolsApiTest {

  public static final String SOS_SYMBOL = "SOS";
  public static final String WETH_TOKEN_ADDRESS = "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2";
  public static final String WETH_SYMBOL = "WETH";

  @Test
  void shouldCreateTokenMetadataApi() {
    String apiKey = "apiKey";
    TokenMetadataSymbolsApi tokenMetadataSymbolsApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .metadata()
        .symbols(List.of(SOS_SYMBOL, WETH_SYMBOL));

    assertTrue(tokenMetadataSymbolsApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenMetadataSymbolsApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/metadata/symbols" +
        "?symbols=SOS&symbols=WETH", castedApi.buildUrl());
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWithEmptySymbolsList() {
    String apiKey = "apiKey";
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> MoralisApi
        .apiKey(apiKey)
        .token()
        .metadata()
        .symbols(List.of()));
    assertEquals("Symbols for the TokenMetadataApi must not be empty!", illegalArgumentException.getMessage());
  }

  @Test
  void shouldCreateTokenTransferWalletApiWithQueryParams() {
    String apiKey = "apiKey";

    TokenMetadataSymbolsApi tokenMetadataSymbolsApi = MoralisApi.apiKey(apiKey)
        .token()
        .metadata()
        .symbols(List.of(SOS_SYMBOL, WETH_SYMBOL))
        .chain(Chain.ETH);

    assertTrue(tokenMetadataSymbolsApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenMetadataSymbolsApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/metadata/symbols" +
        "?symbols=SOS&symbols=WETH" +
        "&chain=eth", castedApi.buildUrl());
  }

  @Test
  void shouldGetMetadatas() {
    List<Erc20Metadata> metadatas = MoralisApi.apiKey(getApiKey())
        .token()
        .metadata()
        .symbols(List.of(SOS_SYMBOL, WETH_SYMBOL))
        .chain(Chain.ETH)
        .get();

    assertFalse(metadatas.isEmpty());

    assertTrue(metadatas.stream().anyMatch(data -> WETH_SYMBOL.equals(data.getSymbol())));
    Erc20Metadata metadata = metadatas.stream().filter(data -> WETH_SYMBOL.equals(data.getSymbol())).findFirst().orElseThrow();
    assertEquals(WETH_TOKEN_ADDRESS.toLowerCase(), metadata.getAddress());
    assertEquals("Wrapped Ether", metadata.getName());
    assertEquals(WETH_SYMBOL, metadata.getSymbol());
    assertEquals(new BigInteger("18"), metadata.getDecimals());
    assertEquals("https://cdn.moralis.io/eth/0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2.png", metadata.getLogo());
    assertEquals("0a7fc292596820fe066ce8ce3fd6e2ad9d479c2993f905e410ef74f2062a83ec", metadata.getLogo_hash());
    assertEquals("https://cdn.moralis.io/eth/0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2_thumb.png", metadata.getThumbnail());
    assertNull(metadata.getBlock_number());
    assertNull(metadata.getValidated());
    assertEquals("2022-01-20T10:39:55.818Z", metadata.getCreated_at());
    // cannot check the details of SOS, since there are so many random token besides the real one with the same symbol.
    assertTrue(metadatas.stream().anyMatch(data -> SOS_SYMBOL.equals(data.getSymbol())));
  }


  @Test
  void shouldFailWithInvalidKey() {
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey("apiKey")
        .token()
        .metadata()
        .symbols(List.of(SOS_SYMBOL))
        .chain(Chain.ETH)
        .get());

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }
}