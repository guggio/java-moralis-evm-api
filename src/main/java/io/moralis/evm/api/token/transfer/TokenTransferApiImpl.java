package io.moralis.evm.api.token.transfer;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.core.ValidatedAddress;

public class TokenTransferApiImpl extends BaseApiImpl implements TokenTransferApi {
  public TokenTransferApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }

  @Override
  public TokenTransferWalletApi wallet(ValidatedAddress wallet) {
    return new TokenTransferWalletApiImpl(this, wallet);
  }

  @Override
  public TokenTransferContractApi contract(ValidatedAddress contract) {
    return new TokenTransferContractApiImpl(this, contract);
  }
}
