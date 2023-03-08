package io.moralis.evm.api.token;

import io.moralis.evm.api.BaseApi;
import io.moralis.evm.api.BaseApiImpl;
import io.moralis.evm.api.token.allowance.TokenAllowanceApi;
import io.moralis.evm.api.token.allowance.TokenAllowanceApiImpl;
import io.moralis.evm.api.token.balance.TokenBalanceApi;
import io.moralis.evm.api.token.balance.TokenBalanceApiImpl;
import io.moralis.evm.api.token.metadata.TokenMetadataApi;
import io.moralis.evm.api.token.metadata.TokenMetadataApiImpl;
import io.moralis.evm.api.token.price.TokenPriceApi;
import io.moralis.evm.api.token.price.TokenPriceApiImpl;
import io.moralis.evm.api.token.transfer.TokenTransferApi;
import io.moralis.evm.api.token.transfer.TokenTransferApiImpl;
import io.moralis.evm.core.Address;

public class TokenApiImpl extends BaseApiImpl implements TokenApi {

  public TokenApiImpl(BaseApi wrappedApi) {
    super(wrappedApi);
  }

  @Override
  protected String getUrlPath() {
    return "";
  }

  @Override
  public TokenBalanceApi balance(Address address) {
    return new TokenBalanceApiImpl(this, address);
  }

  @Override
  public TokenAllowanceApi allowance(Address contract, Address owner, Address spender) {
    return new TokenAllowanceApiImpl(this, contract, owner, spender);
  }

  @Override
  public TokenPriceApi price(Address contract) {
    return new TokenPriceApiImpl(this, contract);
  }

  @Override
  public TokenTransferApi transfer() {
    return new TokenTransferApiImpl(this);
  }

  @Override
  public TokenMetadataApi metadata() {
    return new TokenMetadataApiImpl(this);
  }

}
