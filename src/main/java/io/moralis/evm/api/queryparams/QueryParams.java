package io.moralis.evm.api.queryparams;

import io.moralis.evm.core.Chain;
import io.moralis.evm.core.Exchange;
import io.moralis.evm.core.ValidatedAddress;

import java.time.LocalDateTime;
import java.util.List;

public interface QueryParams {

  String getKey();

  String getParams();

  static QueryParams chain(Chain chain) {
    return new BaseQueryParams<>("chain", chain);
  }

  static QueryParams fromBlock(long blockNumber) {
    return new BaseQueryParams<>("from_block", blockNumber);
  }

  static QueryParams toBlock(long blockNumber) {
    return new BaseQueryParams<>("to_block", blockNumber);
  }

  static QueryParams addresses(List<ValidatedAddress> validatedAddresses) {
    return new ListQueryParams<>("addresses", validatedAddresses, ValidatedAddress::getAddress);
  }

  static QueryParams tokenAddresses(List<ValidatedAddress> tokenAddresses) {
    return new ListQueryParams<>("token_addresses", tokenAddresses, ValidatedAddress::getAddress);
  }

  static QueryParams walletAddresses(List<ValidatedAddress> walletAddresses) {
    return new ListQueryParams<>("wallet_addresses", walletAddresses, ValidatedAddress::getAddress);
  }

  static QueryParams ownerAddress(ValidatedAddress owner) {
    return new BaseQueryParams<>("owner_address", owner, ValidatedAddress::getAddress);
  }

  static QueryParams spenderAddress(ValidatedAddress spender) {
    return new BaseQueryParams<>("spender_address", spender, ValidatedAddress::getAddress);
  }

  static QueryParams exchange(Exchange exchange) {
    return new BaseQueryParams<>("exchange", exchange);
  }

  static QueryParams date(LocalDateTime date) {
    return new BaseQueryParams<>("date", date);
  }

  static QueryParams fromDate(LocalDateTime fromDate) {
    return new BaseQueryParams<>("from_date", fromDate);
  }

  static QueryParams toDate(LocalDateTime toDate) {
    return new BaseQueryParams<>("to_date", toDate);
  }

  static QueryParams cursor(String cursor) {
    return new BaseQueryParams<>("cursor", cursor);
  }

  static QueryParams disableTotal(boolean disableTotal) {
    return new BaseQueryParams<>("disable_total", disableTotal);
  }

  static QueryParams pageSize(int pageSize) {
    return new BaseQueryParams<>("limit", pageSize);
  }

  static QueryParams symbols(List<String> symbols) {
    return new ListQueryParams<>("symbols", symbols);
  }

  static QueryParams includeInternalTx() {
    return new BaseQueryParams<>("include", "internal_transactions");
  }
}
