package io.moralis.evm.api.block;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.core.BlockHash;

import java.time.LocalDateTime;

public class BlockApiImpl extends BaseApiImpl implements BlockApi {

  public BlockApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  public BlockByHashApi blockNumber(long blockNumber) {
    return BlockByHashApiImpl.ofBlockNumber(this, blockNumber);
  }

  @Override
  public BlockByHashApi hash(BlockHash blockHash) {
    return BlockByHashApiImpl.ofBlockHash(this, blockHash);
  }

  @Override
  public BlockByDateApi date(LocalDateTime date) {
    return new BlockByDateApiImpl(this, date);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }
}
