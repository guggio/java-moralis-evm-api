package io.moralis.evm.model;

import lombok.Setter;

import java.math.BigInteger;

@Setter
public class NativeBalance {

  private String balance;

  public BigInteger getBalance() {
    return balance.isBlank() ? BigInteger.ZERO : new BigInteger(balance);
  }
}
