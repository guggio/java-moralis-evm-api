package io.moralis.evm.api.transaction.nativeTx;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TransactionCollection;

import java.time.LocalDateTime;

public class NativeTransactionApiImpl extends ExecutingApi implements NativeTransactionApi {

  private final Address wallet;

  public NativeTransactionApiImpl(BaseApi wrappedApi, Address wallet) {
    super(wrappedApi);
    this.wallet = wallet;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return String.format("/%s", wallet.getAddress());
  }

  @Override
  public TransactionCollection get() {
    return get(TransactionCollection.class);
  }

  @Override
  public NativeTransactionApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public NativeTransactionApi fromBlock(long blockNumber) {
    addQuery(QueryParams.fromBlock(blockNumber));
    return this;
  }

  @Override
  public NativeTransactionApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  public NativeTransactionApi fromDate(LocalDateTime fromDate) {
    addQuery(QueryParams.fromDate(fromDate));
    return this;
  }

  @Override
  public NativeTransactionApi toDate(LocalDateTime toDate) {
    addQuery(QueryParams.toDate(toDate));
    return this;
  }

  @Override
  public NativeTransactionApi cursor(String cursor) {
    addQuery(QueryParams.cursor(cursor));
    return this;
  }

  @Override
  public NativeTransactionApi disableTotal(boolean disableTotal) {
    addQuery(QueryParams.disableTotal(disableTotal));
    return this;
  }

  @Override
  public NativeTransactionApi includeInternalTx(boolean include) {
    QueryParams queryParams = QueryParams.includeInternalTx();
    if (include) {
      addQuery(queryParams);
    } else {
      removeQuery(queryParams);
    }
    return this;
  }

  @Override
  public NativeTransactionApi pageSize(int pageSize) {
    addQuery(QueryParams.pageSize(pageSize));
    return this;
  }
}
