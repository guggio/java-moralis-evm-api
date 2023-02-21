package io.moralis.evm.model;

import java.math.BigInteger;

@SuppressWarnings("unused")
public class TokenBalance {

  private String token_address;
  private String name;
  private String symbol;
  private String logo;
  private String thumbnail;
  private BigInteger decimals;
  private BigInteger balance;

  public String getToken_address() {
    return token_address;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getLogo() {
    return logo;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public BigInteger getDecimals() {
    return decimals;
  }

  public BigInteger getBalance() {
    return balance;
  }
}
