package io.moralis.evm.api.transaction;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.transaction.nativeTx.NativeTransactionApi;
import io.moralis.evm.api.transaction.nativeTx.NativeTransactionApiImpl;
import io.moralis.evm.core.Address;

public class TransactionApiImpl extends BaseApiImpl implements TransactionApi {

  public TransactionApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  public NativeTransactionApi nativeTransactions(Address wallet) {
    return new NativeTransactionApiImpl(this, wallet);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }
}
