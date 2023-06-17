package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.token.price.TokenPriceApi;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.Exchange;
import io.moralis.evm.model.TokenPrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenPriceApiTest {

  public static final String USDC_ETH_ADDRESS = "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48";
  public static final String USDC_BSC_ADDRESS = "0x8ac76a51cc950d9822d68b83fe1ad97b32cd580d";
  public static final String USDC_POLYGON_ADDRESS = "0x2791bca1f2de4661ed88a30c99a7a9449aa84174";

  @ParameterizedTest
  @MethodSource("getValidContractChainExchangeParams")
  void shouldCreateTokenPriceApi(Address contract, Chain chain, Exchange exchange, String expectedUrl) {
    String apiKey = "apiKey";
    TokenPriceApi tokenPriceApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .price(contract)
        .chain(chain)
        .exchange(exchange)
        .toBlock(16686700L);

    assertTrue(tokenPriceApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenPriceApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals(expectedUrl, castedApi.buildUrl());
  }

  @Test
  void shouldGetTokenPriceApiForUsdcEthOnUniswapV2() {
    TokenPrice tokenPrice = MoralisApi
        .apiKey(getApiKey())
        .token()
        .price(Address.of(USDC_ETH_ADDRESS))
        .chain(Chain.ETH)
        .exchange(Exchange.UNISWAP_V2)
        .toBlock(16686700L)
        .get();

    TokenPrice.NativePrice nativePrice = tokenPrice.getNativePrice();
    assertEquals(new BigInteger("617267986827480"), nativePrice.getValue());
    assertEquals(BigInteger.valueOf(18L), nativePrice.getDecimals());
    assertEquals("Ether", nativePrice.getName());
    assertEquals("ETH", nativePrice.getSymbol());
    assertEquals(new BigDecimal("0.998060373458581"), tokenPrice.getUsdPrice());
    assertEquals("0x5C69bEe701ef814a2B6a3EDD4B1652CB9cc5aA6f", tokenPrice.getExchangeAddress());
    assertEquals("Uniswap v2", tokenPrice.getExchangeName());
  }

  @Test
  void shouldGetTokenPriceApiWithDefaultToEthAndUniswapV3() {
    TokenPrice tokenPrice = MoralisApi
        .apiKey(getApiKey())
        .token()
        .price(Address.of(USDC_ETH_ADDRESS))
        .toBlock(16686700L)
        .get();

    TokenPrice.NativePrice nativePrice = tokenPrice.getNativePrice();
    assertEquals(new BigInteger("618688294937677"), nativePrice.getValue());
    assertEquals(BigInteger.valueOf(18L), nativePrice.getDecimals());
    assertEquals("Ether", nativePrice.getName());
    assertEquals("ETH", nativePrice.getSymbol());
    assertEquals(new BigDecimal("1.0005073750047275"), tokenPrice.getUsdPrice());
    assertEquals("0x1f98431c8ad98523631ae4a59f267346ea31f984", tokenPrice.getExchangeAddress());
    assertEquals("Uniswap v3", tokenPrice.getExchangeName());
  }

  @Test
  void shouldGetTokenPriceApiForUsdcBscOnPancakeV2() {
    TokenPrice tokenPrice = MoralisApi
        .apiKey(getApiKey())
        .token()
        .price(Address.of(USDC_BSC_ADDRESS))
        .chain(Chain.BSC)
        .exchange(Exchange.PANCAKESWAP_V2)
        .toBlock(25899865L)
        .get();

    TokenPrice.NativePrice nativePrice = tokenPrice.getNativePrice();
    assertEquals(new BigInteger("3239309284304165"), nativePrice.getValue());
    assertEquals(BigInteger.valueOf(18L), nativePrice.getDecimals());
    assertEquals("Binance Chain Native Token", nativePrice.getName());
    assertEquals("BNB", nativePrice.getSymbol());
    assertEquals(new BigDecimal("1.0005505887577884"), tokenPrice.getUsdPrice());
    assertEquals("0xcA143Ce32Fe78f1f7019d7d551a6402fC5350c73", tokenPrice.getExchangeAddress());
    assertEquals("PancakeSwap v2", tokenPrice.getExchangeName());
  }

  @Test
  void shouldGetTokenPriceApiForUsdcPolygonOnQuickswap() {
    TokenPrice tokenPrice = MoralisApi
        .apiKey(getApiKey())
        .token()
        .price(Address.of(USDC_POLYGON_ADDRESS))
        .chain(Chain.POLYGON)
        .exchange(Exchange.QUICKSWAP)
        .toBlock(39599000L)
        .get();

    // prices from quickswap seem to change randomly, meaning it does not make sense to test those
    TokenPrice.NativePrice nativePrice = tokenPrice.getNativePrice();
    assertEquals(BigInteger.valueOf(18L), nativePrice.getDecimals());
    assertEquals("MATIC", nativePrice.getName());
    assertEquals("MATIC", nativePrice.getSymbol());
    assertEquals("0x5757371414417b8C6CAad45bAeF941aBc7d3Ab32", tokenPrice.getExchangeAddress());
    assertEquals("Quickswap v2", tokenPrice.getExchangeName());
  }

  @Test
  void shouldFailWithInvalidKey() {
    String apiKey = getApiKey();
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey(apiKey + "a")
        .token()
        .price(Address.of(USDC_ETH_ADDRESS))
        .get());

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  @ParameterizedTest
  @MethodSource("getIllegalChainExchangeCombinations")
  void shouldFailWithIllegalChainExchangeCombination(Address contract, Chain chain, Exchange exchange) {
    ConnectionException connectionException = assertThrows(ConnectionException.class,
        () -> MoralisApi
            .apiKey(getApiKey())
            .token()
            .price(contract)
            .chain(chain)
            .exchange(exchange)
            .get());

    assertEquals(400, connectionException.getStatusCode());
    assertEquals("No valid exchange found for the provided chain and/or to_block", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 400 and message: No valid exchange found for the provided chain and/or to_block", connectionException.getMessage());
  }

  @Test
  void shouldFailWhenNoPoolsWereFound() {
    ConnectionException connectionException = assertThrows(ConnectionException.class,
        () -> MoralisApi
            .apiKey(getApiKey())
            .token()
            .price(Address.of(USDC_ETH_ADDRESS))
            .chain(Chain.POLYGON)
            .get());

    assertEquals(404, connectionException.getStatusCode());
    assertEquals("No pools found with enough liquidity, to calculate the price", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 404 and message: No pools found with enough liquidity, to calculate the price", connectionException.getMessage());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }

  public static Stream<Arguments> getValidContractChainExchangeParams() {
    return Stream.of(
        Arguments.of(Address.of(USDC_ETH_ADDRESS), Chain.ETH, Exchange.UNISWAP_V2, "https://deep-index.moralis.io/api/v2/erc20/0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48/price?chain=eth&exchange=uniswap-v2&to_block=16686700"),
        Arguments.of(Address.of(USDC_ETH_ADDRESS), Chain.ETH, Exchange.UNISWAP_V3, "https://deep-index.moralis.io/api/v2/erc20/0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48/price?chain=eth&exchange=uniswap-v3&to_block=16686700"),
        Arguments.of(Address.of(USDC_ETH_ADDRESS), Chain.ETH, Exchange.SUSHISWAP_V2, "https://deep-index.moralis.io/api/v2/erc20/0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48/price?chain=eth&exchange=sushiswap-v2&to_block=16686700"),
        Arguments.of(Address.of(USDC_BSC_ADDRESS), Chain.BSC, Exchange.PANCAKESWAP_V1, "https://deep-index.moralis.io/api/v2/erc20/0x8ac76a51cc950d9822d68b83fe1ad97b32cd580d/price?chain=bsc&exchange=pancakeswap-v1&to_block=16686700"),
        Arguments.of(Address.of(USDC_BSC_ADDRESS), Chain.BSC, Exchange.PANCAKESWAP_V2, "https://deep-index.moralis.io/api/v2/erc20/0x8ac76a51cc950d9822d68b83fe1ad97b32cd580d/price?chain=bsc&exchange=pancakeswap-v2&to_block=16686700"),
        Arguments.of(Address.of(USDC_POLYGON_ADDRESS), Chain.POLYGON, Exchange.QUICKSWAP, "https://deep-index.moralis.io/api/v2/erc20/0x2791bca1f2de4661ed88a30c99a7a9449aa84174/price?chain=polygon&exchange=quickswap&to_block=16686700")
    );
  }

  public static Stream<Arguments> getIllegalChainExchangeCombinations() {
    return Stream.of(
        Arguments.of(Address.of(USDC_BSC_ADDRESS), Chain.BSC, Exchange.QUICKSWAP),
        Arguments.of(Address.of(USDC_ETH_ADDRESS), Chain.ETH, Exchange.PANCAKESWAP_V2),
        Arguments.of(Address.of(USDC_POLYGON_ADDRESS), Chain.POLYGON, Exchange.PANCAKESWAP_V2)
    );
  }
}