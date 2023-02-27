package io.moralis.evm.api.token.transfer;

import io.moralis.evm.core.Address;

public interface TokenTransferApi {

  TokenTransferWalletApi wallet(Address wallet);

}
