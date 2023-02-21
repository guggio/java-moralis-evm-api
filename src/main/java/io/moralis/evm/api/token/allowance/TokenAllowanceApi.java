package io.moralis.evm.api.token.allowance;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.TokenAllowance;

public interface TokenAllowanceApi extends GetApi<TokenAllowance> {

  TokenAllowanceApi chain(Chain chain);
}
