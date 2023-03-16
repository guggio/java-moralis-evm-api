package io.moralis.evm.api.block;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Block;

public interface BlockByHashApi extends GetApi<Block> {

  BlockByHashApi chain(Chain chain);

  BlockByHashApi includeInternalTx(boolean include);
}
