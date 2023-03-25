package io.moralis.evm.api.block;

import io.moralis.evm.core.BlockHash;

import java.time.LocalDateTime;

public interface BlockApi {

  BlockByHashApi blockNumber(long blockNumber);

  BlockByHashApi hash(BlockHash blockHash);

  BlockByDateApi date(LocalDateTime date);
}
