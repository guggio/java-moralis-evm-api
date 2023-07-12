package io.moralis.evm.api.block;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.MoralisApi;
import io.moralis.evm.core.BlockHash;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Block;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockByHashApiTest {

  private static final long BLOCK_NUMBER = 16842847L;
  private static final String BLOCK_HASH = "0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef";

  @Test
  void shouldCreateBlockByNumberApi() {
    String apiKey = "apiKey";
    BlockByHashApi blockByHashApi = MoralisApi.apiKey(apiKey)
        .block()
        .blockNumber(123456L);

    assertTrue(blockByHashApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) blockByHashApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/block/123456", castedApi.buildUrl());
  }

  @Test
  void shouldCreateBlockByNumberApiWithQueryParams() {
    String apiKey = "apiKey";
    BlockByHashApi blockByHashApi = MoralisApi.apiKey(apiKey)
        .block()
        .blockNumber(123456L)
        .chain(Chain.ETH)
        .includeInternalTx(true);

    assertTrue(blockByHashApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) blockByHashApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/block/123456" +
        "?chain=eth&include=internal_transactions", castedApi.buildUrl());
  }

  @Test
  void shouldCreateBlockByHashApi() {
    String apiKey = "apiKey";
    BlockByHashApi blockByHashApi = MoralisApi.apiKey(apiKey)
        .block()
        .hash(BlockHash.of(BLOCK_HASH));

    assertTrue(blockByHashApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) blockByHashApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/block/0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef", castedApi.buildUrl());
  }

  @Test
  void shouldCreateBlockByHashApiWithQueryParams() {
    String apiKey = "apiKey";
    BlockByHashApi blockByHashApi = MoralisApi.apiKey(apiKey)
        .block()
        .hash(BlockHash.of(BLOCK_HASH))
        .chain(Chain.POLYGON)
        .includeInternalTx(true);

    assertTrue(blockByHashApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) blockByHashApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/block/0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef" +
        "?chain=polygon&include=internal_transactions", castedApi.buildUrl());
  }

  @Test
  void shouldRemoveIncludeTag() {
    String apiKey = "apiKey";
    BlockByHashApi blockByHashApi = MoralisApi.apiKey(apiKey)
        .block()
        .hash(BlockHash.of(BLOCK_HASH))
        .chain(Chain.POLYGON)
        .includeInternalTx(true)
        .includeInternalTx(false);

    assertTrue(blockByHashApi instanceof BaseApi);
    BaseApi castedApi = (BaseApi) blockByHashApi;
    assertEquals(apiKey, castedApi.getApiKey());
    assertEquals("https://deep-index.moralis.io/api/v2/block/0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef" +
        "?chain=polygon", castedApi.buildUrl());
  }

  @Test
  void shouldGetBlockByNumber() {
    Block block = MoralisApi.apiKey(getApiKey())
        .block()
        .blockNumber(BLOCK_NUMBER)
        .get();

    assertEquals("2023-03-16T20:42:35.000Z", block.getTimestamp());
    assertEquals(BLOCK_NUMBER, block.getNumber());
    assertEquals(BLOCK_HASH, block.getHash());
    assertEquals("0xfeb873f6aca9f2db2e728662abdf505d4c1dbe65b8e3d5f58b01dd938e44624b", block.getParent_hash());
    assertEquals("0x0000000000000000", block.getNonce());
    assertEquals("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347", block.getSha3_uncles());
    assertEquals("0x48b18b04152753a8d625506aa211942ba2db66cacd1922663acb15e1bcd63d3060d333887ca0a1fa40595810007505609a630a2a8ec0b980760c8900503e9b2d62c329cdc100085cf84350480074c02480b4036524550b06b127bf519866358636002d420e7e8508cc45093d281c0c792e82ca0491790420b0a25ad14839cd981a96c960036cb43c30ef040219c4a83a58b04a8df9000e58f73983e015708e1fcbc951051c126447575968d09d89acfdd40408653af1a52229a20ca414e93f415730c407208a589f0a5c4012127bd3476abcf260082083751c36861a800dfe523873bf1e29910648b2c5d48235002006cc724438d319d275419bc211d4403da9", block.getLogs_bloom());
    assertEquals("0xfb69ec9966c51d900568fc74ea7e879cc5686452bf1a8af14c4dab702ba7da32", block.getTransactions_root());
    assertEquals("0x7debad48e69d5f746e392e97e246cab4f426a87b958ad2350e8bf80450a56510", block.getState_root());
    assertEquals("0x9b3c7b0fd92b1d27ca0e524482f597f74dfd48296edd2216c689d2897e2f5a1c", block.getReceipts_root());
    assertEquals("0xDAFEA492D9c6733ae3d56b7Ed1ADB60692c98Bc5", block.getMiner());
    assertEquals("0", block.getDifficulty());
    assertEquals("58750003716598352816469", block.getTotal_difficulty());
    assertEquals("92886", block.getSize());
    assertEquals("0x496c6c756d696e61746520446d6f63726174697a6520447374726962757465", block.getExtra_data());
    assertEquals(new BigInteger("30000000"), block.getGas_limit());
    assertEquals(new BigInteger("13971684"), block.getGas_used());
    assertEquals(135, block.getTransaction_count());
    assertEquals(block.getTransaction_count(), block.getTransactions().size());
    assertTrue(block.getTransactions().stream().allMatch(transaction -> transaction.getInternal_transactions() == null));
  }

  @Test
  void shouldGetBlockByNumberWithInternalTx() {
    Block block = MoralisApi.apiKey(getApiKey())
        .block()
        .blockNumber(BLOCK_NUMBER)
        .includeInternalTx(true)
        .get();

    assertEquals("2023-03-16T20:42:35.000Z", block.getTimestamp());
    assertEquals(BLOCK_NUMBER, block.getNumber());
    assertEquals(BLOCK_HASH, block.getHash());
    assertEquals("0xfeb873f6aca9f2db2e728662abdf505d4c1dbe65b8e3d5f58b01dd938e44624b", block.getParent_hash());
    assertEquals("0x0000000000000000", block.getNonce());
    assertEquals("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347", block.getSha3_uncles());
    assertEquals("0x48b18b04152753a8d625506aa211942ba2db66cacd1922663acb15e1bcd63d3060d333887ca0a1fa40595810007505609a630a2a8ec0b980760c8900503e9b2d62c329cdc100085cf84350480074c02480b4036524550b06b127bf519866358636002d420e7e8508cc45093d281c0c792e82ca0491790420b0a25ad14839cd981a96c960036cb43c30ef040219c4a83a58b04a8df9000e58f73983e015708e1fcbc951051c126447575968d09d89acfdd40408653af1a52229a20ca414e93f415730c407208a589f0a5c4012127bd3476abcf260082083751c36861a800dfe523873bf1e29910648b2c5d48235002006cc724438d319d275419bc211d4403da9", block.getLogs_bloom());
    assertEquals("0xfb69ec9966c51d900568fc74ea7e879cc5686452bf1a8af14c4dab702ba7da32", block.getTransactions_root());
    assertEquals("0x7debad48e69d5f746e392e97e246cab4f426a87b958ad2350e8bf80450a56510", block.getState_root());
    assertEquals("0x9b3c7b0fd92b1d27ca0e524482f597f74dfd48296edd2216c689d2897e2f5a1c", block.getReceipts_root());
    assertEquals("0xDAFEA492D9c6733ae3d56b7Ed1ADB60692c98Bc5", block.getMiner());
    assertEquals("0", block.getDifficulty());
    assertEquals("58750003716598352816469", block.getTotal_difficulty());
    assertEquals("92886", block.getSize());
    assertEquals("0x496c6c756d696e61746520446d6f63726174697a6520447374726962757465", block.getExtra_data());
    assertEquals(new BigInteger("30000000"), block.getGas_limit());
    assertEquals(new BigInteger("13971684"), block.getGas_used());
    assertEquals(135, block.getTransaction_count());
    assertEquals(block.getTransaction_count(), block.getTransactions().size());
    assertTrue(block.getTransactions().stream().anyMatch(transaction -> transaction.getInternal_transactions() != null));
  }

  @Test
  void shouldGetHashByNumber() {
    Block block = MoralisApi.apiKey(getApiKey())
        .block()
        .hash(BlockHash.of(BLOCK_HASH))
        .get();

    assertEquals("2023-03-16T20:42:35.000Z", block.getTimestamp());
    assertEquals(BLOCK_NUMBER, block.getNumber());
    assertEquals(BLOCK_HASH, block.getHash());
    assertEquals("0xfeb873f6aca9f2db2e728662abdf505d4c1dbe65b8e3d5f58b01dd938e44624b", block.getParent_hash());
    assertEquals("0x0000000000000000", block.getNonce());
    assertEquals("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347", block.getSha3_uncles());
    assertEquals("0x48b18b04152753a8d625506aa211942ba2db66cacd1922663acb15e1bcd63d3060d333887ca0a1fa40595810007505609a630a2a8ec0b980760c8900503e9b2d62c329cdc100085cf84350480074c02480b4036524550b06b127bf519866358636002d420e7e8508cc45093d281c0c792e82ca0491790420b0a25ad14839cd981a96c960036cb43c30ef040219c4a83a58b04a8df9000e58f73983e015708e1fcbc951051c126447575968d09d89acfdd40408653af1a52229a20ca414e93f415730c407208a589f0a5c4012127bd3476abcf260082083751c36861a800dfe523873bf1e29910648b2c5d48235002006cc724438d319d275419bc211d4403da9", block.getLogs_bloom());
    assertEquals("0xfb69ec9966c51d900568fc74ea7e879cc5686452bf1a8af14c4dab702ba7da32", block.getTransactions_root());
    assertEquals("0x7debad48e69d5f746e392e97e246cab4f426a87b958ad2350e8bf80450a56510", block.getState_root());
    assertEquals("0x9b3c7b0fd92b1d27ca0e524482f597f74dfd48296edd2216c689d2897e2f5a1c", block.getReceipts_root());
    assertEquals("0xDAFEA492D9c6733ae3d56b7Ed1ADB60692c98Bc5", block.getMiner());
    assertEquals("0", block.getDifficulty());
    assertEquals("58750003716598352816469", block.getTotal_difficulty());
    assertEquals("92886", block.getSize());
    assertEquals("0x496c6c756d696e61746520446d6f63726174697a6520447374726962757465", block.getExtra_data());
    assertEquals(new BigInteger("30000000"), block.getGas_limit());
    assertEquals(new BigInteger("13971684"), block.getGas_used());
    assertEquals(135, block.getTransaction_count());
    assertEquals(block.getTransaction_count(), block.getTransactions().size());
    assertTrue(block.getTransactions().stream().allMatch(transaction -> transaction.getInternal_transactions() == null));
  }

  @Test
  void shouldGetBlockByHashWithInternalTx() {
    Block block = MoralisApi.apiKey(getApiKey())
        .block()
        .hash(BlockHash.of(BLOCK_HASH))
        .includeInternalTx(true)
        .get();

    assertEquals("2023-03-16T20:42:35.000Z", block.getTimestamp());
    assertEquals(BLOCK_NUMBER, block.getNumber());
    assertEquals(BLOCK_HASH, block.getHash());
    assertEquals("0xfeb873f6aca9f2db2e728662abdf505d4c1dbe65b8e3d5f58b01dd938e44624b", block.getParent_hash());
    assertEquals("0x0000000000000000", block.getNonce());
    assertEquals("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347", block.getSha3_uncles());
    assertEquals("0x48b18b04152753a8d625506aa211942ba2db66cacd1922663acb15e1bcd63d3060d333887ca0a1fa40595810007505609a630a2a8ec0b980760c8900503e9b2d62c329cdc100085cf84350480074c02480b4036524550b06b127bf519866358636002d420e7e8508cc45093d281c0c792e82ca0491790420b0a25ad14839cd981a96c960036cb43c30ef040219c4a83a58b04a8df9000e58f73983e015708e1fcbc951051c126447575968d09d89acfdd40408653af1a52229a20ca414e93f415730c407208a589f0a5c4012127bd3476abcf260082083751c36861a800dfe523873bf1e29910648b2c5d48235002006cc724438d319d275419bc211d4403da9", block.getLogs_bloom());
    assertEquals("0xfb69ec9966c51d900568fc74ea7e879cc5686452bf1a8af14c4dab702ba7da32", block.getTransactions_root());
    assertEquals("0x7debad48e69d5f746e392e97e246cab4f426a87b958ad2350e8bf80450a56510", block.getState_root());
    assertEquals("0x9b3c7b0fd92b1d27ca0e524482f597f74dfd48296edd2216c689d2897e2f5a1c", block.getReceipts_root());
    assertEquals("0xDAFEA492D9c6733ae3d56b7Ed1ADB60692c98Bc5", block.getMiner());
    assertEquals("0", block.getDifficulty());
    assertEquals("58750003716598352816469", block.getTotal_difficulty());
    assertEquals("92886", block.getSize());
    assertEquals("0x496c6c756d696e61746520446d6f63726174697a6520447374726962757465", block.getExtra_data());
    assertEquals(new BigInteger("30000000"), block.getGas_limit());
    assertEquals(new BigInteger("13971684"), block.getGas_used());
    assertEquals(135, block.getTransaction_count());
    assertEquals(block.getTransaction_count(), block.getTransactions().size());
    assertTrue(block.getTransactions().stream().anyMatch(transaction -> transaction.getInternal_transactions() != null));
  }

  private String getApiKey() {
    return System.getenv("API_KEY");
  }

}
