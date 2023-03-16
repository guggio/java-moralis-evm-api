package io.moralis.evm.api.block;

import io.moralis.evm.core.BlockHash;

public interface BlockApi {

  BlockByHashApi blockNumber(long blockNumber);

  BlockByHashApi hash(BlockHash blockHash);
}
