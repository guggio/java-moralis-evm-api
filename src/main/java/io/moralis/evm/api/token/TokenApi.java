package io.moralis.evm.api.token;

import io.moralis.evm.api.token.allowance.TokenAllowanceApi;
import io.moralis.evm.api.token.balance.TokenBalanceApi;
import io.moralis.evm.api.token.metadata.TokenMetadataApi;
import io.moralis.evm.api.token.price.TokenPriceApi;
import io.moralis.evm.api.token.transfer.TokenTransferApi;
import io.moralis.evm.core.ValidatedAddress;

public interface TokenApi {

  TokenBalanceApi balance(ValidatedAddress validatedAddress);

  TokenAllowanceApi allowance(ValidatedAddress contract, ValidatedAddress owner, ValidatedAddress spender);

  TokenPriceApi price(ValidatedAddress contract);

  TokenTransferApi transfer();

  TokenMetadataApi metadata();
}
