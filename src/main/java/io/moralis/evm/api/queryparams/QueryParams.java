package io.moralis.evm.api.queryparams;

import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;

import java.util.List;

public interface QueryParams {

  String getKey();

  String getParams();

  static QueryParams chain(Chain chain) {
    return new BaseQueryParams<>("chain", chain);
  }

  static QueryParams toBlock(long blockNumber) {
    return new BaseQueryParams<>("to_block", blockNumber);
  }

  static QueryParams tokenAddresses(List<Address> tokenAddresses) {
    return new ListQueryParams<>("token_addresses", tokenAddresses, Address::getAddress);
  }

}
