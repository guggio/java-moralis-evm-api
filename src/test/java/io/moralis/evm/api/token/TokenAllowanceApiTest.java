package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.token.allowance.TokenAllowanceApi;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TokenAllowance;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenAllowanceApiTest {

  public static final String CONTRACT_ADDRESS = "0x675BBC7514013E2073DB7a919F6e4cbeF576de37";
  public static final String OWNER_ADDRESS = "0x11F813A59d746eb051f1249E17C01ED4ecb7637A";
  public static final String SPENDER_ADDRESS = "0x7a250d5630B4cF539739dF2C5dAcb4c659F2488D";

  @Test
  void shouldCreateTokenAllowanceApi() {
    String apiKey = "apiKey";
    TokenAllowanceApi tokenAllowanceApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .allowance(Address.of(CONTRACT_ADDRESS),
            Address.of(OWNER_ADDRESS),
            Address.of(SPENDER_ADDRESS));

    assertTrue(tokenAllowanceApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenAllowanceApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/0x675BBC7514013E2073DB7a919F6e4cbeF576de37/allowance?owner_address=0x11F813A59d746eb051f1249E17C01ED4ecb7637A&spender_address=0x7a250d5630B4cF539739dF2C5dAcb4c659F2488D", castedApi.buildUrl());
  }

  @Test
  void shouldCreateTokenAllowanceApiWithChain() {
    String apiKey = "apiKey";
    TokenAllowanceApi tokenAllowanceApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .allowance(Address.of(CONTRACT_ADDRESS),
            Address.of(OWNER_ADDRESS),
            Address.of(SPENDER_ADDRESS))
        .chain(Chain.ETH);

    assertTrue(tokenAllowanceApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenAllowanceApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/0x675BBC7514013E2073DB7a919F6e4cbeF576de37/allowance?owner_address=0x11F813A59d746eb051f1249E17C01ED4ecb7637A&spender_address=0x7a250d5630B4cF539739dF2C5dAcb4c659F2488D&chain=eth", castedApi.buildUrl());
  }

  @Test
  void shouldGetTokenAllowance() {
    String apiKey = getApiKey();
    TokenAllowance tokenAllowance = MoralisApi
        .apiKey(apiKey)
        .token()
        .allowance(Address.of(CONTRACT_ADDRESS),
            Address.of(OWNER_ADDRESS),
            Address.of(SPENDER_ADDRESS))
        .chain(Chain.ETH)
        .get();

    assertNotNull(tokenAllowance);
    assertEquals(new BigInteger("5568000000000000000000"), tokenAllowance.getAllowance());
  }

  @Test
  void shouldFailWithInvalidKey() {
    String apiKey = getApiKey();
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey(apiKey + "a")
        .token()
        .allowance(Address.of(CONTRACT_ADDRESS),
            Address.of(OWNER_ADDRESS),
            Address.of(SPENDER_ADDRESS))
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