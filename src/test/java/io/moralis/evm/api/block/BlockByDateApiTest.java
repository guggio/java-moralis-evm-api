package io.moralis.evm.api.block;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.api.exception.ConnectionException;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.BlockByDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockByDateApiTest {

  public static final LocalDateTime DATE = LocalDateTime.of(2023, Month.MARCH, 24, 15, 33);

  @Test
  void shouldCreateBlockByDateApi() {
    String apiKey = "apiKey";
    BlockByDateApi blockByDateApi = MoralisApi.apiKey(apiKey)
        .block()
        .date(DATE);

    assertTrue(blockByDateApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) blockByDateApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/dateToBlock?date=2023-03-24T15:33", castedApi.buildUrl());
  }

  @Test
  void shouldCreateBlockByNumberApiWithQueryParams() {
    String apiKey = "apiKey";
    BlockByDateApi blockByDateApi = MoralisApi.apiKey(apiKey)
        .block()
        .date(DATE)
        .chain(Chain.POLYGON);

    assertTrue(blockByDateApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) blockByDateApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/dateToBlock?date=2023-03-24T15:33&chain=polygon", castedApi.buildUrl());
  }

  @Test
  void shouldGetBlockByDate() {
    BlockByDate blockByDate = MoralisApi.apiKey(getApiKey())
        .block()
        .date(DATE)
        .chain(Chain.POLYGON)
        .get();

    assertEquals("2023-03-24T15:33:00+00:00", blockByDate.getDate());
    assertEquals(40717400L, blockByDate.getBlock());
    assertEquals(1679671980L, blockByDate.getTimestamp());
    assertEquals("2023-03-24T15:32:57.000Z", blockByDate.getBlock_timestamp());
    assertEquals("0x561fcf57c5fb84d62d220701ba25aa27c9d41c1ff4e51463b290ebca393d5961", blockByDate.getHash());
    assertEquals("0x8766d857b095ca2155ccedb385a46e0a8cfbd4b61bc57df0fa9ff9d3790916e2", blockByDate.getParent_hash());
  }

  @Test
  void shouldFailWithInvalidKey() {
    String apiKey = getApiKey();
    ConnectionException connectionException = assertThrows(ConnectionException.class, () -> MoralisApi
        .apiKey(apiKey + "a")
        .block()
        .date(DATE)
        .get());

    assertEquals(401, connectionException.getStatusCode());
    assertEquals("Invalid key", connectionException.getApiErrorMessage().getMessage());
    assertEquals("Request failed with status code 401 and message: Invalid key", connectionException.getMessage());
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }

}
