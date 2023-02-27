package io.moralis.evm.api.token.transfer;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20TransactionCollection;

import java.time.LocalDateTime;

public class TokenTransferWalletApiImpl extends ExecutingApi implements TokenTransferWalletApi {

  private final Address wallet;

  public TokenTransferWalletApiImpl(BaseApi wrappedApi, Address wallet) {
    super(wrappedApi);
    this.wallet = wallet;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/" + wallet.getAddress() + "/erc20/transfers";
  }


  @Override
  public TokenTransferWalletApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenTransferWalletApi fromBlock(long blockNumber) {
    addQuery(QueryParams.fromBlock(blockNumber));
    return this;
  }

  @Override
  public TokenTransferWalletApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  public TokenTransferWalletApi fromDate(LocalDateTime fromDate) {
    addQuery(QueryParams.fromDate(fromDate));
    return this;
  }

  @Override
  public TokenTransferWalletApi toDate(LocalDateTime toDate) {
    addQuery(QueryParams.toDate(toDate));
    return this;
  }

  @Override
  public TokenTransferWalletApi pageSize(int pageSize) {
    addQuery(QueryParams.pageSize(pageSize));
    return this;
  }

  @Override
  public TokenTransferWalletApi disableTotal(boolean disableTotal) {
    addQuery(QueryParams.disableTotal(disableTotal));
    return this;
  }

  @Override
  public TokenTransferWalletApi cursor(String cursor) {
    addQuery(QueryParams.cursor(cursor));
    return this;
  }

  @Override
  public Erc20TransactionCollection get() {
    return get(Erc20TransactionCollection.class);
  }
}
