package io.moralis.evm.api.transaction;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.transaction.nativeTx.NativeTransactionApi;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.InternalTransaction;
import io.moralis.evm.model.Transaction;
import io.moralis.evm.model.TransactionCollection;
import org.junit.jupiter.api.BeforeEach;
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

class NativeTransactionApiTest {

  public static final String WALLET_ADDRESS = "0xa2B13834161fD407218cf642C2D17060b26aeA09";

  @BeforeEach
  void setUp() throws InterruptedException {
    // ugly hack to circumvent api rate limits until fixed
    Thread.sleep(500);
  }

  @Test
  void shouldCreateNativeTransactionsApi() {
    String apiKey = "apiKey";
    NativeTransactionApi nativeTransactionApi = MoralisApi
        .apiKey(apiKey)
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS));

    assertTrue(nativeTransactionApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) nativeTransactionApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09", castedApi.buildUrl());
  }

  @Test
  void shouldCreateNativeTransactionsApiWithQueryParams() {
    String apiKey = "apiKey";
    NativeTransactionApi nativeTransactionApi = MoralisApi
        .apiKey(apiKey)
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16365015L)
        .toBlock(16672386L)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .disableTotal(false)
        .cursor("abc");

    assertTrue(nativeTransactionApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) nativeTransactionApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09" +
        "?chain=eth&from_block=16365015&to_block=16672386" +
        "&from_date=2023-01-08T22:20:11&to_date=2023-02-20T21:19:11&limit=10&disable_total=false&cursor=abc", castedApi.buildUrl());
  }

  @Test
  void shouldGetNativeTransactionsPerWallet() {
    TransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672322L)
        .toBlock(16672386L)
        // same dates as in the next test, but not relevant for the query when block numbers used
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getTotal());
    assertNull(transactionCollection.getCursor());
    assertEquals(0, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());

    List<Transaction> transactions = transactionCollection.getResult();
    assertEquals(1, transactions.size());

    Transaction transaction = transactions.get(0);
    assertEquals("0xfe84cf396cf6e928f5d9811226c39c04c6a7a277f965719b63c3225636cd359d", transaction.getHash());
    assertEquals(7, transaction.getNonce());
    assertEquals(86, transaction.getTransaction_index());
    assertEquals("0xa2b13834161fd407218cf642c2d17060b26aea09", transaction.getFrom_address());
    assertEquals("0x5427fefa711eff984124bfbb1ab6fbf5e3da1820", transaction.getTo_address());
    assertEquals(BigInteger.ZERO, transaction.getValue());
    assertEquals(new BigInteger("102417"), transaction.getGas());
    assertEquals(new BigInteger("29424676305"), transaction.getGas_price());
    assertEquals("0xa5977fbb000000000000000000000000a2b13834161fd407218cf642c2d17060b26aea09000000000000000000000000a0b86991c6218b36c1d19d4a2e9eb0ce3606eb48000000000000000000000000000000000000000000000000000000014d30a180000000000000000000000000000000000000000000000000000000000000a86a0000000000000000000000000000000000000000000000000000018670b15f88000000000000000000000000000000000000000000000000000000000000278d",
        transaction.getInput());
    assertEquals(new BigInteger("9932341"), transaction.getReceipt_cumulative_gas_used());
    assertEquals(new BigInteger("97435"), transaction.getReceipt_gas_used());
    assertNull(transaction.getReceipt_contract_address());
    assertNull(transaction.getReceipt_root());
    assertEquals(1, transaction.getReceipt_status());
    assertEquals("2023-02-20T21:19:11.000Z", transaction.getBlock_timestamp());
    assertEquals(16672386, transaction.getBlock_number());
    assertEquals("0x50df3e6174b8fa417582dfa7e47ee625099ea79df3898adcd75bc9477d54f760", transaction.getBlock_hash());
    assertTrue(transaction.getInternal_transactions().isEmpty());
  }

  @Test
  void shouldGetNativeTransactionsWithInternalTx() {
    long blockNumber = 16672386L;
    String blockHash = "0x50df3e6174b8fa417582dfa7e47ee625099ea79df3898adcd75bc9477d54f760";
    TransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672322L)
        .toBlock(blockNumber)
        // same dates as in the next test, but not relevant for the query when block numbers used
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .includeInternalTx(true)
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getTotal());
    assertNull(transactionCollection.getCursor());
    assertEquals(0, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());

    List<Transaction> transactions = transactionCollection.getResult();
    assertEquals(1, transactions.size());

    Transaction transaction = transactions.get(0);
    assertEquals("0xfe84cf396cf6e928f5d9811226c39c04c6a7a277f965719b63c3225636cd359d", transaction.getHash());
    assertEquals(7, transaction.getNonce());
    assertEquals(86, transaction.getTransaction_index());
    assertEquals("0xa2b13834161fd407218cf642c2d17060b26aea09", transaction.getFrom_address());
    assertEquals("0x5427fefa711eff984124bfbb1ab6fbf5e3da1820", transaction.getTo_address());
    assertEquals(BigInteger.ZERO, transaction.getValue());
    assertEquals(new BigInteger("102417"), transaction.getGas());
    assertEquals(new BigInteger("29424676305"), transaction.getGas_price());
    assertEquals("0xa5977fbb000000000000000000000000a2b13834161fd407218cf642c2d17060b26aea09000000000000000000000000a0b86991c6218b36c1d19d4a2e9eb0ce3606eb48000000000000000000000000000000000000000000000000000000014d30a180000000000000000000000000000000000000000000000000000000000000a86a0000000000000000000000000000000000000000000000000000018670b15f88000000000000000000000000000000000000000000000000000000000000278d",
        transaction.getInput());
    assertEquals(new BigInteger("9932341"), transaction.getReceipt_cumulative_gas_used());
    assertEquals(new BigInteger("97435"), transaction.getReceipt_gas_used());
    assertNull(transaction.getReceipt_contract_address());
    assertNull(transaction.getReceipt_root());
    assertEquals(1, transaction.getReceipt_status());
    assertEquals("2023-02-20T21:19:11.000Z", transaction.getBlock_timestamp());
    assertEquals(blockNumber, transaction.getBlock_number());
    assertEquals(blockHash, transaction.getBlock_hash());
    assertEquals(2, transaction.getInternal_transactions().size());

    InternalTransaction internalTransaction = transaction.getInternal_transactions().get(0);
    assertEquals("0xfe84cf396cf6e928f5d9811226c39c04c6a7a277f965719b63c3225636cd359d", internalTransaction.getTransaction_hash());
    assertEquals(blockNumber, internalTransaction.getBlock_number());
    assertEquals(blockHash, internalTransaction.getBlock_hash());
    assertEquals("CALL", internalTransaction.getType());
    assertEquals("0x5427fefa711eff984124bfbb1ab6fbf5e3da1820", internalTransaction.getFrom());
    assertEquals("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48", internalTransaction.getTo());
    assertEquals(BigInteger.ZERO, internalTransaction.getValue());
    assertEquals(new BigInteger("38096"), internalTransaction.getGas());
    assertEquals(new BigInteger("32892"), internalTransaction.getGas_used());
    assertEquals("0x23b872dd000000000000000000000000a2b13834161fd407218cf642c2d17060b26aea090000000000000000000000005427fefa711eff984124bfbb1ab6fbf5e3da1820000000000000000000000000000000000000000000000000000000014d30a180",
        internalTransaction.getInput());
    assertEquals("0x0000000000000000000000000000000000000000000000000000000000000001", internalTransaction.getOutput());
  }

  @Test
  void shouldGetNativeTransactionsWithTotal() {
    TransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672322L)
        .toBlock(16672386L)
        // same dates as in the next test, but not relevant for the query when block numbers used
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .disableTotal(false)
        .get();

    assertNotNull(transactionCollection);
    assertEquals(1, transactionCollection.getTotal());
    assertNull(transactionCollection.getCursor());
    assertEquals(0, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());

    List<Transaction> transactions = transactionCollection.getResult();
    assertEquals(1, transactions.size());

    Transaction transaction = transactions.get(0);
    assertEquals("0xfe84cf396cf6e928f5d9811226c39c04c6a7a277f965719b63c3225636cd359d", transaction.getHash());
    assertEquals(7, transaction.getNonce());
    assertEquals(86, transaction.getTransaction_index());
    assertEquals("0xa2b13834161fd407218cf642c2d17060b26aea09", transaction.getFrom_address());
    assertEquals("0x5427fefa711eff984124bfbb1ab6fbf5e3da1820", transaction.getTo_address());
    assertEquals(BigInteger.ZERO, transaction.getValue());
    assertEquals(new BigInteger("102417"), transaction.getGas());
    assertEquals(new BigInteger("29424676305"), transaction.getGas_price());
    assertEquals("0xa5977fbb000000000000000000000000a2b13834161fd407218cf642c2d17060b26aea09000000000000000000000000a0b86991c6218b36c1d19d4a2e9eb0ce3606eb48000000000000000000000000000000000000000000000000000000014d30a180000000000000000000000000000000000000000000000000000000000000a86a0000000000000000000000000000000000000000000000000000018670b15f88000000000000000000000000000000000000000000000000000000000000278d",
        transaction.getInput());
    assertEquals(new BigInteger("9932341"), transaction.getReceipt_cumulative_gas_used());
    assertEquals(new BigInteger("97435"), transaction.getReceipt_gas_used());
    assertNull(transaction.getReceipt_contract_address());
    assertNull(transaction.getReceipt_root());
    assertEquals(1, transaction.getReceipt_status());
    assertEquals("2023-02-20T21:19:11.000Z", transaction.getBlock_timestamp());
    assertEquals(16672386, transaction.getBlock_number());
    assertEquals("0x50df3e6174b8fa417582dfa7e47ee625099ea79df3898adcd75bc9477d54f760", transaction.getBlock_hash());
    assertTrue(transaction.getInternal_transactions().isEmpty());
  }

  @Test
  void shouldGetNativeTransactionsWithDates() {
    TransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getTotal());
    assertNull(transactionCollection.getCursor());
    assertEquals(0, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());
    assertEquals(6, transactionCollection.getResult().size());
  }

  @Test
  void shouldLoopOverAllPages() {
    NativeTransactionApi nativeTransactionApi = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(5);

    TransactionCollection transactionCollection = nativeTransactionApi.get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getTotal());
    assertNotNull(transactionCollection.getCursor());
    assertEquals(0, transactionCollection.getPage());
    assertEquals(5, transactionCollection.getPage_size());
    assertEquals(5, transactionCollection.getResult().size());

    transactionCollection = nativeTransactionApi
        .cursor(transactionCollection.getCursor())
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getTotal());
    assertNull(transactionCollection.getCursor());
    assertEquals(1, transactionCollection.getPage());
    assertEquals(5, transactionCollection.getPage_size());
    assertEquals(1, transactionCollection.getResult().size());
  }

  @Test
  void shouldGetEmptyResultWithFromBlockLargerThanToBlock() {
    TransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672386L)
        .toBlock(16672322L)
        .pageSize(10)
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getTotal());
    assertEquals(0, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());
    assertTrue(transactionCollection.getResult().isEmpty());
  }

  @Test
  void shouldFailWithInvalidKey() {
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey("apiKey")
        .transaction()
        .nativeTransactions(Address.of(WALLET_ADDRESS))
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