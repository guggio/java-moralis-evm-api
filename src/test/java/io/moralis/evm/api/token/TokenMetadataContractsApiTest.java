package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.token.metadata.TokenMetadataContractsApi;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20Metadata;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenMetadataContractsApiTest {

  public static final String SOS_TOKEN_ADDRESS = "0x3b484b82567a09e2588A13D54D032153f0c0aEe0";
  public static final String WETH_TOKEN_ADDRESS = "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2";

  @Test
  void shouldCreateTokenMetadataApi() {
    String apiKey = "apiKey";
    TokenMetadataContractsApi tokenMetadataContractsApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .metadata()
        .contracts(List.of(Address.of(SOS_TOKEN_ADDRESS), Address.of(WETH_TOKEN_ADDRESS)));

    assertTrue(tokenMetadataContractsApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenMetadataContractsApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/metadata" +
        "?addresses=0x3b484b82567a09e2588A13D54D032153f0c0aEe0&addresses=0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", castedApi.buildUrl());
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWithEmptyAddressesList() {
    String apiKey = "apiKey";
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> MoralisApi
        .apiKey(apiKey)
        .token()
        .metadata()
        .contracts(List.of()));
    assertEquals("Addresses for the TokenMetadataApi must not be empty!", illegalArgumentException.getMessage());
  }

  @Test
  void shouldCreateTokenTransferWalletApiWithQueryParams() {
    String apiKey = "apiKey";

    TokenMetadataContractsApi tokenMetadataContractsApi = MoralisApi.apiKey(apiKey)
        .token()
        .metadata()
        .contracts(List.of(Address.of(SOS_TOKEN_ADDRESS), Address.of(WETH_TOKEN_ADDRESS)))
        .chain(Chain.ETH);

    assertTrue(tokenMetadataContractsApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenMetadataContractsApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/metadata" +
        "?addresses=0x3b484b82567a09e2588A13D54D032153f0c0aEe0&addresses=0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2" +
        "&chain=eth", castedApi.buildUrl());
  }

  @Test
  void shouldGetMetadatas() {
    List<Erc20Metadata> metadatas = MoralisApi.apiKey(getApiKey())
        .token()
        .metadata()
        .contracts(List.of(Address.of(WETH_TOKEN_ADDRESS), Address.of(SOS_TOKEN_ADDRESS)))
        .chain(Chain.ETH)
        .get();

    assertEquals(2, metadatas.size());

    Erc20Metadata metadata = metadatas.get(0);
    assertMetadata(metadata,
        SOS_TOKEN_ADDRESS.toLowerCase(),
        "SOS",
        "SOS",
        new BigInteger("18"),
        null,
        null,
        null,
        13860522L,
        1, "2022-01-20T10:39:55.818Z"
    );
    assertMetadata(metadatas.get(1),
        WETH_TOKEN_ADDRESS.toLowerCase(),
        "Wrapped Ether",
        "WETH",
        new BigInteger("18"),
        "https://cdn.moralis.io/eth/0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2.png",
        "0a7fc292596820fe066ce8ce3fd6e2ad9d479c2993f905e410ef74f2062a83ec",
        "https://cdn.moralis.io/eth/0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2_thumb.png",
        null,
        null, "2022-01-20T10:39:55.818Z"
    );
  }

  @Test
  void shouldReturnEmptyMetadataForNotFoundAddressOnChain() {
    List<Erc20Metadata> metadatas = MoralisApi.apiKey(getApiKey())
        .token()
        .metadata()
        .contracts(List.of(Address.of(WETH_TOKEN_ADDRESS)))
        .chain(Chain.POLYGON)
        .get();

    assertEquals(1, metadatas.size());
    assertMetadata(metadatas.get(0),
        WETH_TOKEN_ADDRESS.toLowerCase(),
        "",
        "",
        BigInteger.ZERO,
        null,
        null,
        null,
        null,
        null,
        null);
  }

  private void assertMetadata(Erc20Metadata metadata,
                              String address,
                              String name,
                              String symbol,
                              BigInteger decimals,
                              String logo,
                              String logoHash,
                              String thumbnail,
                              Long blockNumber,
                              Integer validated,
                              String createdAt) {
    assertEquals(address, metadata.getAddress());
    assertEquals(name, metadata.getName());
    assertEquals(symbol, metadata.getSymbol());
    assertEquals(decimals, metadata.getDecimals());
    assertEquals(logo, metadata.getLogo());
    assertEquals(logoHash, metadata.getLogoHash());
    assertEquals(thumbnail, metadata.getThumbnail());
    assertEquals(blockNumber, metadata.getBlockNumber());
    assertEquals(validated, metadata.getValidated());
    assertEquals(createdAt, metadata.getCreatedAt());
  }

  @Test
  void shouldFailWithInvalidKey() {
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey("apiKey")
        .token()
        .metadata()
        .contracts(List.of(Address.of(SOS_TOKEN_ADDRESS)))
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