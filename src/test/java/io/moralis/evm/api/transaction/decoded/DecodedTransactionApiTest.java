package io.moralis.evm.api.transaction.decoded;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.DecodedEvent;
import io.moralis.evm.model.DecodedTransaction;
import io.moralis.evm.model.DecodedTransactionCollection;
import io.moralis.evm.model.EventParam;
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

class DecodedTransactionApiTest {

  public static final String WALLET_ADDRESS = "0xa2B13834161fD407218cf642C2D17060b26aeA09";

  @BeforeEach
  void setUp() throws InterruptedException {
    // ugly hack to circumvent api rate limits until fixed
    Thread.sleep(500);
  }

  @Test
  void shouldCreateDecodedTransactionsApi() {
    String apiKey = "apiKey";
    DecodedTransactionApi decodedTransactionApi = MoralisApi
        .apiKey(apiKey)
        .transaction()
        .decodedTransactions(Address.of(WALLET_ADDRESS));

    assertTrue(decodedTransactionApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) decodedTransactionApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/verbose", castedApi.buildUrl());
  }

  @Test
  void shouldCreateDecodedTransactionsApiWithQueryParams() {
    String apiKey = "apiKey";
    DecodedTransactionApi decodedTransactionApi = MoralisApi
        .apiKey(apiKey)
        .transaction()
        .decodedTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16365015L)
        .toBlock(16672386L)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .disableTotal(false)
        .cursor("abc");

    assertTrue(decodedTransactionApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) decodedTransactionApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/0xa2B13834161fD407218cf642C2D17060b26aeA09/verbose" +
        "?chain=eth&from_block=16365015&to_block=16672386" +
        "&from_date=2023-01-08T22:20:11&to_date=2023-02-20T21:19:11&limit=10&disable_total=false&cursor=abc", castedApi.buildUrl());
  }

  @Test
  void shouldGetDecodedTransactionsPerWallet() {
    DecodedTransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .decodedTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672322L)
        .toBlock(16672386L)
        // same dates as in the next test, but not relevant for the query when block numbers used
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getCursor());
    assertEquals(1, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());

    List<DecodedTransaction> transactions = transactionCollection.getResult();
    assertEquals(1, transactions.size());

    DecodedTransaction transaction = transactions.get(0);
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
    DecodedEvent decodedCall = transaction.getDecoded_call();
    assertEquals("send(address,address,uint256,uint64,uint64,uint32)", decodedCall.getSignature());
    assertEquals("send", decodedCall.getLabel());
    assertEquals("function", decodedCall.getType());
    List<EventParam> params = decodedCall.getParams();
    assertEquals(6, params.size());
    assertParam(params.get(0), "_receiver", "0xa2B13834161fD407218cf642C2D17060b26aeA09", "address");
    assertParam(params.get(1), "_token", "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48", "address");
    assertParam(params.get(2), "_amount", "5590000000", "uint256");
    assertParam(params.get(3), "_dstChainId", "43114", "uint64");
    assertParam(params.get(4), "_nonce", "1676927917960", "uint64");
    assertParam(params.get(5), "_maxSlippage", "10125", "uint32");
  }

  @Test
  void shouldGetDecodedTransactionsWithDates() {
    DecodedTransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .decodedTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getCursor());
    assertEquals(1, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());
    assertEquals(6, transactionCollection.getResult().size());
  }

  @Test
  void shouldLoopOverAllPages() {
    DecodedTransactionApi nativeTransactionApi = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .decodedTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(5);

    DecodedTransactionCollection transactionCollection = nativeTransactionApi.get();

    assertNotNull(transactionCollection);
    assertNotNull(transactionCollection.getCursor());
    assertEquals(1, transactionCollection.getPage());
    assertEquals(5, transactionCollection.getPage_size());
    assertEquals(5, transactionCollection.getResult().size());

    transactionCollection = nativeTransactionApi
        .cursor(transactionCollection.getCursor())
        .get();

    assertNotNull(transactionCollection);
    assertNull(transactionCollection.getCursor());
    assertEquals(2, transactionCollection.getPage());
    assertEquals(5, transactionCollection.getPage_size());
    assertEquals(1, transactionCollection.getResult().size());
  }

  @Test
  void shouldGetEmptyResultWithFromBlockLargerThanToBlock() {
    DecodedTransactionCollection transactionCollection = MoralisApi
        .apiKey(getApiKey())
        .transaction()
        .decodedTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16672386L)
        .toBlock(16672322L)
        .pageSize(10)
        .get();

    assertNotNull(transactionCollection);
    assertEquals(1, transactionCollection.getPage());
    assertEquals(10, transactionCollection.getPage_size());
    assertTrue(transactionCollection.getResult().isEmpty());
  }

  @Test
  void shouldFailWithInvalidKey() {
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey("apiKey")
        .transaction()
        .decodedTransactions(Address.of(WALLET_ADDRESS))
        .chain(Chain.ETH)
        .toBlock(16678160L)
        .get());

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  private void assertParam(EventParam param, String expectedName, String expectedValue, String expectedType) {
    assertEquals(expectedName, param.getName());
    assertEquals(expectedValue, param.getValue());
    assertEquals(expectedType, param.getType());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }
}