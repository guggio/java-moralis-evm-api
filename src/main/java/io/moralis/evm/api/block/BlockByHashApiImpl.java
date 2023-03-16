package io.moralis.evm.api.block;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.BlockHash;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Block;

public class BlockByHashApiImpl extends ExecutingApi implements BlockByHashApi {

  private final String blockOrHash;

  BlockByHashApiImpl(BaseApi wrappedApi, String blockOrHash) {
    super(wrappedApi);
    this.blockOrHash = blockOrHash;
  }

  static BlockByHashApi ofBlockNumber(BaseApi wrappedApi, long blockNumber) {
    return new BlockByHashApiImpl(wrappedApi, String.valueOf(blockNumber));
  }

  static BlockByHashApi ofBlockHash(BaseApi wrappedApi, BlockHash blockHash) {
    return new BlockByHashApiImpl(wrappedApi, blockHash.getHash());
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/block/" + blockOrHash;
  }

  @Override
  public BlockByHashApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public BlockByHashApi includeInternalTx(boolean include) {
    QueryParams queryParams = QueryParams.includeInternalTx();
    if (include) {
      addQuery(queryParams);
    } else {
      removeQuery(queryParams);
    }
    return this;
  }

  @Override
  public Block get() {
    return get(Block.class);
  }
}
