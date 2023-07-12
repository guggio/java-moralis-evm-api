package io.moralis.evm.api.balance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.NativeBalance;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BalanceOneAddressApiTest {

  private static final String WALLET_ADDRESS = "0xa2B13834161fD407218cf642C2D17060b26aeA09";
  private static final long BLOCK_NUMBER = 14657000L;

  @Test
  void shouldCreateApi() {
    String apiKey = "apiKey";
    BalanceOneAddressApi balanceOneAddressApi = MoralisApi.apiKey(apiKey)
        .balance()
        .address(ValidatedAddress.of(WALLET_ADDRESS));

    assertTrue(balanceOneAddressApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) balanceOneAddressApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/balance", castedApi.buildUrl());
  }

  @Test
  void shouldCreateApiWithQueryParams() {
    String apiKey = "apiKey";
    BalanceOneAddressApi balanceOneAddressApi = MoralisApi.apiKey(apiKey)
        .balance()
        .address(ValidatedAddress.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .toBlock(BLOCK_NUMBER);

    assertTrue(balanceOneAddressApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) balanceOneAddressApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/balance?chain=eth&to_block=14657000", castedApi.buildUrl());
  }

  @Test
  void shouldGetBalanceOfAddress() {
    NativeBalance nativeBalance = MoralisApi.apiKey(getApiKey())
        .balance()
        .address(ValidatedAddress.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .toBlock(BLOCK_NUMBER)
        .get();

    assertEquals(new BigInteger("50000000000000000"), nativeBalance.getBalance());
  }

  @Test
  void shouldFailWithInvalidKey() {
    String apiKey = getApiKey();
    BalanceOneAddressApi api = MoralisApi
        .apiKey(apiKey + "a")
        .balance()
        .address(ValidatedAddress.of(WALLET_ADDRESS));
    ConnectionException connectionException = assertThrows(ConnectionException.class, api::get);

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }

}
