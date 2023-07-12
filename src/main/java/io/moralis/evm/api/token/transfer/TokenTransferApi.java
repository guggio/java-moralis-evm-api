package io.moralis.evm.api.token.transfer;

import io.moralis.evm.core.ValidatedAddress;

public interface TokenTransferApi {

  TokenTransferWalletApi wallet(ValidatedAddress wallet);

  TokenTransferContractApi contract(ValidatedAddress contract);

}
