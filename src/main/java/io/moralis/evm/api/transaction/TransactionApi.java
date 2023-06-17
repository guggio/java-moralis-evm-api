package io.moralis.evm.api.transaction;

import io.moralis.evm.api.transaction.decoded.DecodedTransactionApi;
import io.moralis.evm.api.transaction.nativeTx.NativeTransactionApi;
import io.moralis.evm.core.Address;

public interface TransactionApi {

  NativeTransactionApi nativeTransactions(Address wallet);

  DecodedTransactionApi decodedTransactions(Address wallet);
}
