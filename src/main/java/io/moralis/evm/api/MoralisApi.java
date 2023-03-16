package io.moralis.evm.api;

import io.moralis.evm.api.block.BlockApi;
import io.moralis.evm.api.token.TokenApi;

/**
 * Starting point for every EVM Api query.
 */
public interface MoralisApi {

  TokenApi token();

  BlockApi block();

  static MoralisApi apiKey(String apiKey) {
    return new MoralisApiV2Impl(apiKey);
  }

}
