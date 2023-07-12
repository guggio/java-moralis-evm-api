package io.moralis.evm.api.transaction;

import io.moralis.evm.api.transaction.decoded.DecodedTransactionApi;
import io.moralis.evm.api.transaction.nativetx.NativeTransactionApi;
import io.moralis.evm.core.ValidatedAddress;

public interface TransactionApi {

  NativeTransactionApi nativeTransactions(ValidatedAddress wallet);

  DecodedTransactionApi decodedTransactions(ValidatedAddress wallet);
}
