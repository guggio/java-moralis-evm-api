package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.token.transfer.TokenTransferWalletApi;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20Transaction;
import io.moralis.evm.model.Erc20TransactionCollection;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenTransferWalletApiTest {

  public static final String WALLET_ADDRESS = "0xa2B13834161fD407218cf642C2D17060b26aeA09";

  @Test
  void shouldCreateTokenTransferWalletApi() {
    String apiKey = "apiKey";
    TokenTransferWalletApi tokenTransferWalletApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS));

    assertTrue(tokenTransferWalletApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenTransferWalletApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/erc20/transfers", castedApi.buildUrl());
  }

  @Test
  void shouldCreateTokenTransferWalletApiWithQueryParams() {
    String apiKey = "apiKey";
    TokenTransferWalletApi tokenTransferWalletApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16365015L)
        .toBlock(16672386L)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .disableTotal(false)
        .cursor("abc");

    assertTrue(tokenTransferWalletApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenTransferWalletApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/erc20/transfers" +
        "?chain=eth&from_block=16365015&to_block=16672386" +
        "&from_date=2023-01-08T22:20:11&to_date=2023-02-20T21:19:11&limit=10&disable_total=false&cursor=abc", castedApi.buildUrl());
  }

  @Test
  void shouldGetTokenTransfersPerWallet() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672322L)
        .toBlock(16672386L)
        // same dates as in the next test, but not relevant for the query when block numbers used
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertNull(erc20TransactionCollection.getCursor());
    assertEquals(0, erc20TransactionCollection.getPage());
    assertEquals(10, erc20TransactionCollection.getPageSize());
    List<Erc20Transaction> erc20Transactions = erc20TransactionCollection.getResult();
    assertEquals(1, erc20Transactions.size());

    Erc20Transaction erc20Transaction = erc20Transactions.get(0);
    assertEquals("0xfe84cf396cf6e928f5d9811226c39c04c6a7a277f965719b63c3225636cd359d", erc20Transaction.getTransactionHash());
    assertEquals("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48", erc20Transaction.getAddress());
    assertEquals("2023-02-20T21:19:11.000Z", erc20Transaction.getBlockTimestamp());
    assertEquals(16672386L, erc20Transaction.getBlockNumber());
    assertEquals("0x50df3e6174b8fa417582dfa7e47ee625099ea79df3898adcd75bc9477d54f760", erc20Transaction.getBlockHash());
    assertEquals("0x5427fefa711eff984124bfbb1ab6fbf5e3da1820", erc20Transaction.getToAddress());
    assertEquals("0xa2b13834161fd407218cf642c2d17060b26aea09", erc20Transaction.getFromAddress());
    assertEquals(new BigInteger("5590000000"), erc20Transaction.getValue());
    assertEquals(86, erc20Transaction.getTransactionIndex());
    assertEquals(211, erc20Transaction.getLogIndex());
  }

  @Test
  void shouldGetTokenTransfersPerWalletWithTotal() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672322L)
        .toBlock(16672386L)
        // same dates as in the next test, but not relevant for the query when block numbers used
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .disableTotal(false)
        .get();

    assertNotNull(erc20TransactionCollection);
    assertNotNull(erc20TransactionCollection.getTotal());
    assertEquals(1, erc20TransactionCollection.getTotal());
    assertNull(erc20TransactionCollection.getCursor());
    assertEquals(0, erc20TransactionCollection.getPage());
    assertEquals(10, erc20TransactionCollection.getPageSize());
    List<Erc20Transaction> erc20Transactions = erc20TransactionCollection.getResult();
    assertEquals(1, erc20Transactions.size());

    Erc20Transaction erc20Transaction = erc20Transactions.get(0);
    assertEquals("0xfe84cf396cf6e928f5d9811226c39c04c6a7a277f965719b63c3225636cd359d", erc20Transaction.getTransactionHash());
    assertEquals("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48", erc20Transaction.getAddress());
    assertEquals("2023-02-20T21:19:11.000Z", erc20Transaction.getBlockTimestamp());
    assertEquals(16672386L, erc20Transaction.getBlockNumber());
    assertEquals("0x50df3e6174b8fa417582dfa7e47ee625099ea79df3898adcd75bc9477d54f760", erc20Transaction.getBlockHash());
    assertEquals("0x5427fefa711eff984124bfbb1ab6fbf5e3da1820", erc20Transaction.getToAddress());
    assertEquals("0xa2b13834161fd407218cf642c2d17060b26aea09", erc20Transaction.getFromAddress());
    assertEquals(new BigInteger("5590000000"), erc20Transaction.getValue());
    assertEquals(86, erc20Transaction.getTransactionIndex());
    assertEquals(211, erc20Transaction.getLogIndex());
  }

  @Test
  void shouldGetTokenTransfersPerWalletWithDates() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertNull(erc20TransactionCollection.getCursor());
    assertEquals(0, erc20TransactionCollection.getPage());
    assertEquals(10, erc20TransactionCollection.getPageSize());
    List<Erc20Transaction> erc20Transactions = erc20TransactionCollection.getResult();
    assertEquals(9, erc20Transactions.size());
  }

  @Test
  void shouldLoopOverAllPages() {
    TokenTransferWalletApi tokenTransferWalletApi = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(5);

    Erc20TransactionCollection erc20TransactionCollection = tokenTransferWalletApi.get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertNotNull(erc20TransactionCollection.getCursor());
    assertEquals(0, erc20TransactionCollection.getPage());
    assertEquals(5, erc20TransactionCollection.getPageSize());
    assertEquals(5, erc20TransactionCollection.getResult().size());

    erc20TransactionCollection = tokenTransferWalletApi
        .cursor(erc20TransactionCollection.getCursor())
        .get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertNull(erc20TransactionCollection.getCursor());
    assertEquals(1, erc20TransactionCollection.getPage());
    assertEquals(5, erc20TransactionCollection.getPageSize());
    assertEquals(4, erc20TransactionCollection.getResult().size());
  }

  @Test
  void shouldGetEmptyResultWithFromBlockLargerThanToBlock() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672386L)
        .toBlock(16672322L)
        .pageSize(10)
        .get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertEquals(0, erc20TransactionCollection.getPage());
    assertEquals(10, erc20TransactionCollection.getPageSize());
    assertTrue(erc20TransactionCollection.getResult().isEmpty());
  }

  @Test
  void shouldFailWithInvalidKey() {
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey("apiKey")
        .token()
        .transfer()
        .wallet(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .toBlock(16678160L)
        .get());

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }
}