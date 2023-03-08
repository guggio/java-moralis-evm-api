package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.api.token.transfer.TokenTransferContractApi;
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

class TokenTransferContractApiTest {

  public static final String SOS_TOKEN_ADDRESS = "0x3b484b82567a09e2588A13D54D032153f0c0aEe0";

  @Test
  void shouldCreateTokenTransferWalletApi() {
    String apiKey = "apiKey";
    TokenTransferContractApi tokenTransferContractApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .transfer()
        .contract(Address.of(SOS_TOKEN_ADDRESS));

    assertTrue(tokenTransferContractApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenTransferContractApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/0x3b484b82567a09e2588A13D54D032153f0c0aEe0/transfers", castedApi.buildUrl());
  }

  @Test
  void shouldCreateTokenTransferWalletApiWithQueryParams() {
    String apiKey = "apiKey";
    TokenTransferContractApi tokenTransferContractApi = MoralisApi
        .apiKey(apiKey)
        .token()
        .transfer()
        .contract(Address.of(SOS_TOKEN_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16365015L)
        .toBlock(16672386L)
        .fromDate(LocalDateTime.of(2023, Month.JANUARY, 8, 22, 20, 11))
        .toDate(LocalDateTime.of(2023, Month.FEBRUARY, 20, 21, 19, 11))
        .pageSize(10)
        .disableTotal(false)
        .cursor("abc");

    assertTrue(tokenTransferContractApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) tokenTransferContractApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/erc20/0x3b484b82567a09e2588A13D54D032153f0c0aEe0/transfers" +
        "?chain=eth&from_block=16365015&to_block=16672386" +
        "&from_date=2023-01-08T22:20:11&to_date=2023-02-20T21:19:11&limit=10&disable_total=false&cursor=abc", castedApi.buildUrl());
  }

  @Test
  void shouldGetTokenTransfersPerWallet() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .contract(Address.of(SOS_TOKEN_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16785262L)
        .toBlock(16785262L)
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
    assertEquals("0x9c8362558fd10320a015d70f0ffab98f57a55f33bdb75539da0e434e6b673265", erc20Transaction.getTransactionHash());
    assertEquals("0x3b484b82567a09e2588a13d54d032153f0c0aee0", erc20Transaction.getAddress());
    assertEquals("2023-03-08T18:17:35.000Z", erc20Transaction.getBlockTimestamp());
    assertEquals(16785262L, erc20Transaction.getBlockNumber());
    assertEquals("0x3e1ecd34716c9b1d58f8507840ad6329d8d10af54dc7a48ededda43cbaf2247f", erc20Transaction.getBlockHash());
    assertEquals("0x41d0cae8510b745903781a95d24adf3a5905a67a", erc20Transaction.getToAddress());
    assertEquals("0x6cc5f688a315f3dc28a7781717a9a798a59fda7b", erc20Transaction.getFromAddress());
    assertEquals(new BigInteger("1138774670847252400000000000"), erc20Transaction.getValue());
    assertEquals(45, erc20Transaction.getTransactionIndex());
    assertEquals(134, erc20Transaction.getLogIndex());
  }

  @Test
  void shouldGetTokenTransfersPerWalletWithTotal() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .contract(Address.of(SOS_TOKEN_ADDRESS))
        .chain(Chain.ETH)
        .fromBlock(16785262L)
        .toBlock(16785262L)
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
    assertEquals("0x9c8362558fd10320a015d70f0ffab98f57a55f33bdb75539da0e434e6b673265", erc20Transaction.getTransactionHash());
    assertEquals("0x3b484b82567a09e2588a13d54d032153f0c0aee0", erc20Transaction.getAddress());
    assertEquals("2023-03-08T18:17:35.000Z", erc20Transaction.getBlockTimestamp());
    assertEquals(16785262L, erc20Transaction.getBlockNumber());
    assertEquals("0x3e1ecd34716c9b1d58f8507840ad6329d8d10af54dc7a48ededda43cbaf2247f", erc20Transaction.getBlockHash());
    assertEquals("0x41d0cae8510b745903781a95d24adf3a5905a67a", erc20Transaction.getToAddress());
    assertEquals("0x6cc5f688a315f3dc28a7781717a9a798a59fda7b", erc20Transaction.getFromAddress());
    assertEquals(new BigInteger("1138774670847252400000000000"), erc20Transaction.getValue());
    assertEquals(45, erc20Transaction.getTransactionIndex());
    assertEquals(134, erc20Transaction.getLogIndex());
  }

  @Test
  void shouldGetTokenTransfersPerWalletWithDates() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .contract(Address.of(SOS_TOKEN_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.MARCH, 8, 18, 17, 35))
        .toDate(LocalDateTime.of(2023, Month.MARCH, 8, 19, 17, 35))
        .pageSize(10)
        .get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertNull(erc20TransactionCollection.getCursor());
    assertEquals(0, erc20TransactionCollection.getPage());
    assertEquals(10, erc20TransactionCollection.getPageSize());
    List<Erc20Transaction> erc20Transactions = erc20TransactionCollection.getResult();
    assertEquals(2, erc20Transactions.size());
  }

  @Test
  void shouldLoopOverAllPages() {
    TokenTransferContractApi tokenTransferContractApi = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .contract(Address.of(SOS_TOKEN_ADDRESS))
        .chain(Chain.ETH)
        .fromDate(LocalDateTime.of(2023, Month.MARCH, 8, 9, 17, 35))
        .toDate(LocalDateTime.of(2023, Month.MARCH, 8, 19, 17, 35))
        .pageSize(10);

    Erc20TransactionCollection erc20TransactionCollection = tokenTransferContractApi.get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertNotNull(erc20TransactionCollection.getCursor());
    assertEquals(0, erc20TransactionCollection.getPage());
    assertEquals(10, erc20TransactionCollection.getPageSize());
    assertEquals(10, erc20TransactionCollection.getResult().size());

    erc20TransactionCollection = tokenTransferContractApi
        .cursor(erc20TransactionCollection.getCursor())
        .get();

    assertNotNull(erc20TransactionCollection);
    assertNull(erc20TransactionCollection.getTotal());
    assertNull(erc20TransactionCollection.getCursor());
    assertEquals(1, erc20TransactionCollection.getPage());
    assertEquals(10, erc20TransactionCollection.getPageSize());
    assertEquals(9, erc20TransactionCollection.getResult().size());
  }

  @Test
  void shouldGetEmptyResultWithFromBlockLargerThanToBlock() {
    Erc20TransactionCollection erc20TransactionCollection = MoralisApi
        .apiKey(getApiKey())
        .token()
        .transfer()
        .contract(Address.of(SOS_TOKEN_ADDRESS))
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
        .contract(Address.of(SOS_TOKEN_ADDRESS))
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