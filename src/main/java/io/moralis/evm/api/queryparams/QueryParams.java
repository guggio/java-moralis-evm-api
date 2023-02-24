package io.moralis.evm.api.queryparams;

import io.moralis.evm.core.Address;
import io.moralis.evm.core.Chain;
import io.moralis.evm.core.Exchange;

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

  static QueryParams ownerAddress(Address owner) {
    return new BaseQueryParams<>("owner_address", owner, Address::getAddress);
  }

  static QueryParams spenderAddress(Address spender) {
    return new BaseQueryParams<>("spender_address", spender, Address::getAddress);
  }

  static QueryParams exchange(Exchange exchange) {
    return new BaseQueryParams<>("exchange", exchange);
  }
}
