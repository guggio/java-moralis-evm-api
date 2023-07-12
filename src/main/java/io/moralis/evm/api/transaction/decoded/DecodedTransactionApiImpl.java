package io.moralis.evm.api.transaction.decoded;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.ValidatedAddress;
import io.moralis.evm.model.DecodedTransactionCollection;

import java.time.LocalDateTime;

public class DecodedTransactionApiImpl extends ExecutingApi implements DecodedTransactionApi {

  private final ValidatedAddress wallet;

  public DecodedTransactionApiImpl(BaseApi wrappedApi, ValidatedAddress wallet) {
    super(wrappedApi);
    this.wallet = wallet;
  }

  @Override
  public DecodedTransactionCollection get() {
    return get(DecodedTransactionCollection.class);
  }

  @Override
  public DecodedTransactionApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public DecodedTransactionApi fromBlock(long blockNumber) {
    addQuery(QueryParams.fromBlock(blockNumber));
    return this;
  }

  @Override
  public DecodedTransactionApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  public DecodedTransactionApi fromDate(LocalDateTime fromDate) {
    addQuery(QueryParams.fromDate(fromDate));
    return this;
  }

  @Override
  public DecodedTransactionApi toDate(LocalDateTime toDate) {
    addQuery(QueryParams.toDate(toDate));
    return this;
  }

  @Override
  public DecodedTransactionApi cursor(String cursor) {
    addQuery(QueryParams.cursor(cursor));
    return this;
  }

  @Override
  public DecodedTransactionApi disableTotal(boolean disableTotal) {
    addQuery(QueryParams.disableTotal(disableTotal));
    return this;
  }

  @Override
  public DecodedTransactionApi pageSize(int pageSize) {
    addQuery(QueryParams.pageSize(pageSize));
    return this;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return String.format("/%s/verbose", wallet.getAddress());
  }
}
