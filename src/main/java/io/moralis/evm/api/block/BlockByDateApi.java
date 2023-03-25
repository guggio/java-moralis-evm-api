package io.moralis.evm.api.block;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.BlockByDate;

public interface BlockByDateApi extends GetApi<BlockByDate> {

  BlockByDateApi chain(Chain chain);
}
