package io.moralis.evm.api.block;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.BlockByDate;

import java.time.LocalDateTime;

public class BlockByDateApiImpl extends ExecutingApi implements BlockByDateApi {

  BlockByDateApiImpl(BaseApi wrappedApi, LocalDateTime date) {
    super(wrappedApi);
    addQuery(QueryParams.date(date));
  }

  @Override
  public BlockByDateApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/dateToBlock";
  }

  @Override
  public BlockByDate get() {
    return get(BlockByDate.class);
  }
}
