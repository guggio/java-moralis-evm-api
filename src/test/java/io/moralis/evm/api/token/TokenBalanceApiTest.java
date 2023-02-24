package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.token.balance.TokenBalanceApi;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TokenBalance;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenBalanceApiTest {

  public static final String WALLET_ADDRESS = "0xa2B13834161fD407218cf642C2D17060b26aeA09";
  public static final String USDC_ADDRESS = "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48";
  public static final String RANDOM_TOKEN_ADDRESS = "0xFc0d6Cf33e38bcE7CA7D89c0E292274031b7157A";

  @Test
  void shouldCreateBaseUrlForMoralisApi() {
    String apiKey = "apiKey";
    MoralisApi moralisApi = MoralisApi
        .apiKey(apiKey);

    assertTrue(moralisApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) moralisApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2", castedApi.buildUrl());
  }

  @Test
  void shouldCreateBaseUrlForTokenApi() {
    String apiKey = "apiKey";
    TokenApi tokenApi = MoralisApi
        .apiKey(apiKey)
        .token();

    assertTrue(tokenApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2", castedApi.buildUrl());
  }

  @Test
  void shouldCreateTokenBalanceApi() {
    String apiKey = "apiKey";
    TokenBalanceApi tokenBalanceApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .balance(Address.of(WALLET_ADDRESS));

    assertTrue(tokenBalanceApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenBalanceApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/erc20", castedApi.buildUrl());
  }

  @Test
  void shouldCreateTokenBalanceApiWithQueryParams() {
    String apiKey = "apiKey";
    TokenBalanceApi tokenBalanceApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .balance(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .toBlock(123456L)
        .tokenAddress(List.of(Address.of(USDC_ADDRESS), Address.of(RANDOM_TOKEN_ADDRESS)));

    assertTrue(tokenBalanceApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenBalanceApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/erc20?chain=eth&to_block=123456&token_addresses=0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48&token_addresses=0xFc0d6Cf33e38bcE7CA7D89c0E292274031b7157A", castedApi.buildUrl());
  }

  @Test
  void shouldCreateTokenBalanceApiWithOnlyChainParam() {
    String apiKey = "apiKey";
    TokenBalanceApi tokenBalanceApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .balance(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH);

    assertTrue(tokenBalanceApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenBalanceApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/erc20?chain=eth", castedApi.buildUrl());
  }

  @Test
  void shouldCreateTokenBalanceApiWithOnlyLatestParamOption() {
    String apiKey = "apiKey";
    TokenBalanceApi tokenBalanceApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .balance(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .chain(Chain.POLYGON);

    assertTrue(tokenBalanceApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenBalanceApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/erc20?chain=polygon", castedApi.buildUrl());
  }

  @Test
  void shouldGetTokenBalances() {
    String key = getApiKey();

    List<TokenBalance> tokenBalances = MoralisApi
        .apiKey(key)
        .token()
        .balance(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .toBlock(16678160L)
        .tokenAddress(List.of(Address.of(USDC_ADDRESS), Address.of(RANDOM_TOKEN_ADDRESS)))
        .get();

    assertEquals(2, tokenBalances.size());
    assertTokenBalance(tokenBalances.get(0), USDC_ADDRESS.toLowerCase(), "USD Coin", "USDC", "https://cdn.moralis.io/eth/0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48.png", "https://cdn.moralis.io/eth/0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48_thumb.png", BigInteger.valueOf(6), BigInteger.valueOf(8560143L));
    assertTokenBalance(tokenBalances.get(1), RANDOM_TOKEN_ADDRESS.toLowerCase(), "NETVRK", "NTVRK", null, null, BigInteger.valueOf(18), new BigInteger("436799153657618867194"));
  }

  @Test
  void shouldFailWithInvalidKey() {
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey("apiKey")
        .token()
        .balance(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .toBlock(16678160L)
        .tokenAddress(List.of(Address.of(USDC_ADDRESS), Address.of(RANDOM_TOKEN_ADDRESS)))
        .get());

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  private void assertTokenBalance(TokenBalance tokenBalance,
                                  String expectedTokenAddress,
                                  String expectedName,
                                  String expectedSymbol,
                                  String expectedLogo,
                                  String expectedThumbnail,
                                  BigInteger expectedDecimals,
                                  BigInteger expectedBalance) {
    assertEquals(expectedTokenAddress, tokenBalance.getToken_address());
    assertEquals(expectedName, tokenBalance.getName());
    assertEquals(expectedSymbol, tokenBalance.getSymbol());
    assertEquals(expectedLogo, tokenBalance.getLogo());
    assertEquals(expectedThumbnail, tokenBalance.getThumbnail());
    assertEquals(expectedDecimals, tokenBalance.getDecimals());
    assertEquals(expectedBalance, tokenBalance.getBalance());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }
}