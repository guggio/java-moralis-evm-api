package io.moralis.evm.api.transaction.nativetx;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TransactionCollection;

import java.time.LocalDateTime;

public interface NativeTransactionApi extends GetApi<TransactionCollection> {

  NativeTransactionApi chain(Chain chain);

  NativeTransactionApi fromBlock(long blockNumber);

  NativeTransactionApi toBlock(long blockNumber);

  NativeTransactionApi fromDate(LocalDateTime fromDate);

  NativeTransactionApi toDate(LocalDateTime toDate);

  NativeTransactionApi cursor(String cursor);

  NativeTransactionApi disableTotal(boolean disableTotal);

  NativeTransactionApi includeInternalTx(boolean include);

  NativeTransactionApi pageSize(int pageSize);
}
