package io.moralis.evm.api.transaction;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.transaction.decoded.DecodedTransactionApi;
import io.moralis.evm.api.transaction.decoded.DecodedTransactionApiImpl;
import io.moralis.evm.api.transaction.nativetx.NativeTransactionApi;
import io.moralis.evm.api.transaction.nativetx.NativeTransactionApiImpl;
import io.moralis.evm.core.ValidatedAddress;

public class TransactionApiImpl extends BaseApiImpl implements TransactionApi {

  public TransactionApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  public NativeTransactionApi nativeTransactions(ValidatedAddress wallet) {
    return new NativeTransactionApiImpl(this, wallet);
  }

  @Override
  public DecodedTransactionApi decodedTransactions(ValidatedAddress wallet) {
    return new DecodedTransactionApiImpl(this, wallet);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }
}
