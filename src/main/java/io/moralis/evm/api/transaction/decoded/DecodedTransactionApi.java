package io.moralis.evm.api.transaction.decoded;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.DecodedTransactionCollection;

import java.time.LocalDateTime;

public interface DecodedTransactionApi extends GetApi<DecodedTransactionCollection> {

  DecodedTransactionApi chain(Chain chain);

  DecodedTransactionApi fromBlock(long blockNumber);

  DecodedTransactionApi toBlock(long blockNumber);

  DecodedTransactionApi fromDate(LocalDateTime fromDate);

  DecodedTransactionApi toDate(LocalDateTime toDate);

  DecodedTransactionApi cursor(String cursor);

  DecodedTransactionApi disableTotal(boolean disableTotal);

  DecodedTransactionApi pageSize(int pageSize);

}
