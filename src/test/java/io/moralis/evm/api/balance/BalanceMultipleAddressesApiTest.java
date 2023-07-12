package io.moralis.evm.api.balance;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.NativeBalances;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BalanceMultipleAddressesApiTest {

  private static final String WALLET_ADDRESS = "0xa2B13834161fD407218cf642C2D17060b26aeA09";
  private static final String OTHER_WALLET_ADDRESS = "0x474b91ce978dda0adddfa0eb9e3543280713fcae";
  private static final long BLOCK_NUMBER = 14657000L;

  @Test
  void shouldCreateApi() {
    String apiKey = "apiKey";
    BalanceMultipleAddressesApi balanceMultipleAddressesApi = MoralisApi.apiKey(apiKey)
        .balance()
        .addresses(List.of(ValidatedAddress.of(WALLET_ADDRESS), ValidatedAddress.of(OTHER_WALLET_ADDRESS)));

    assertTrue(balanceMultipleAddressesApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) balanceMultipleAddressesApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/wallets/balances" +
        "?wallet_addresses=0xa2B13834161fD407218cf642C2D17060b26aeA09&wallet_addresses=0x474b91ce978dda0adddfa0eb9e3543280713fcae", castedApi.buildUrl());
  }

  @Test
  void shouldCreateApiWithQueryParams() {
    String apiKey = "apiKey";
    BalanceMultipleAddressesApi balanceMultipleAddressesApi = MoralisApi.apiKey(apiKey)
        .balance()
        .addresses(List.of(ValidatedAddress.of(WALLET_ADDRESS), ValidatedAddress.of(OTHER_WALLET_ADDRESS)))
        .chain(Chain.ETH)
        .toBlock(BLOCK_NUMBER);

    assertTrue(balanceMultipleAddressesApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) balanceMultipleAddressesApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/wallets/balances" +
        "?wallet_addresses=0xa2B13834161fD407218cf642C2D17060b26aeA09&wallet_addresses=0x474b91ce978dda0adddfa0eb9e3543280713fcae" +
        "&chain=eth&to_block=14657000", castedApi.buildUrl());  }

  @Test
  void shouldGetBalanceOfAddress() {
    List<NativeBalances> nativeBalances = MoralisApi.apiKey(getApiKey())
        .balance()
        .addresses(List.of(ValidatedAddress.of(WALLET_ADDRESS), ValidatedAddress.of(OTHER_WALLET_ADDRESS)))
        .chain(Chain.ETH)
        .toBlock(BLOCK_NUMBER)
        .get();

    assertEquals(1, nativeBalances.size());

    NativeBalances nativeBalance = nativeBalances.get(0);
    assertEquals("Eth", nativeBalance.getChain());
    assertEquals("0x1", nativeBalance.getChain_id());
    assertEquals(new BigInteger("144506515315432054"), nativeBalance.getTotal_balance());
    assertEquals(BLOCK_NUMBER, nativeBalance.getBlock_number());
    assertEquals("1650933509", nativeBalance.getBlock_timestamp());
    assertEquals(new BigDecimal("0.144506515315432054"), nativeBalance.getTotal_balance_formatted());

    List<NativeBalances.WalletBalance> walletBalances = nativeBalance.getWallet_balances();
    assertEquals(2, walletBalances.size());
    assertWalletBalance(walletBalances.get(0),
        WALLET_ADDRESS.toLowerCase(),
        new BigInteger("50000000000000000"),
        BigDecimal.valueOf(0.05));
    assertWalletBalance(walletBalances.get(1),
        OTHER_WALLET_ADDRESS.toLowerCase(),
        new BigInteger("94506515315432054"),
        new BigDecimal("0.094506515315432054"));

  }

  @Test
  void shouldFailWithInvalidKey() {
    String apiKey = getApiKey();
    BalanceMultipleAddressesApi api = MoralisApi
        .apiKey(apiKey + "a")
        .balance()
        .addresses(List.of(ValidatedAddress.of(WALLET_ADDRESS), ValidatedAddress.of(OTHER_WALLET_ADDRESS)));
    ConnectionException connectionException = assertThrows(ConnectionException.class, api::get);

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  private void assertWalletBalance(NativeBalances.WalletBalance walletBalance,
                                   String expectedAddress,
                                   BigInteger expectedBalance,
                                   BigDecimal expectedBalanceFormatted) {
    assertEquals(expectedAddress, walletBalance.getAddress());
    assertEquals(expectedBalance, walletBalance.getBalance());
    assertEquals(expectedBalanceFormatted, walletBalance.getBalance_formatted());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }

}
