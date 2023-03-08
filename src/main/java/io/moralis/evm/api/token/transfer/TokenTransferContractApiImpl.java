package io.moralis.evm.api.token.transfer;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.ExecutingApi;
import io.moralis.evm.api.queryparams.QueryParams;
import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20TransactionCollection;

import java.time.LocalDateTime;

public class TokenTransferContractApiImpl extends ExecutingApi implements TokenTransferContractApi {

  private final Address contract;

  TokenTransferContractApiImpl(BaseApi wrappedApi, Address contract) {
    super(wrappedApi);
    this.contract = contract;
  }

  @Override
  public Erc20TransactionCollection get() {
    return get(Erc20TransactionCollection.class);
  }

  @Override
  public TokenTransferContractApi chain(Chain chain) {
    addQuery(QueryParams.chain(chain));
    return this;
  }

  @Override
  public TokenTransferContractApi fromBlock(long blockNumber) {
    addQuery(QueryParams.fromBlock(blockNumber));
    return this;
  }

  @Override
  public TokenTransferContractApi toBlock(long blockNumber) {
    addQuery(QueryParams.toBlock(blockNumber));
    return this;
  }

  @Override
  public TokenTransferContractApi fromDate(LocalDateTime fromDate) {
    addQuery(QueryParams.fromDate(fromDate));
    return this;
  }

  @Override
  public TokenTransferContractApi toDate(LocalDateTime toDate) {
    addQuery(QueryParams.toDate(toDate));
    return this;
  }

  @Override
  public TokenTransferContractApi pageSize(int pageSize) {
    addQuery(QueryParams.pageSize(pageSize));
    return this;
  }

  @Override
  public TokenTransferContractApi disableTotal(boolean disableTotal) {
    addQuery(QueryParams.disableTotal(disableTotal));
    return this;
  }

  @Override
  public TokenTransferContractApi cursor(String cursor) {
    addQuery(QueryParams.cursor(cursor));
    return this;
  }

  @Override
  protected String getUrlPartBeforeQueryParams() {
    return "/erc20/" + contract.getAddress() + "/transfers";
  }
}
