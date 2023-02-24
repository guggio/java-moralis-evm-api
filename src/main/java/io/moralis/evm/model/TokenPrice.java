package io.moralis.evm.model;

import java.math.BigDecimal;
import java.math.BigInteger;

@SuppressWarnings("unused")
public class TokenPrice {

  private NativePrice nativePrice;
  private BigDecimal usdPrice;
  private String exchangeAddress;
  private String exchangeName;

  public NativePrice getNativePrice() {
    return nativePrice;
  }

  public BigDecimal getUsdPrice() {
    return usdPrice;
  }

  public String getExchangeAddress() {
    return exchangeAddress;
  }

  public String getExchangeName() {
    return exchangeName;
  }

  public static class NativePrice {

    private BigInteger value;
    private BigInteger decimals;
    private String name;
    private String symbol;

    public BigInteger getValue() {
      return value;
    }

    public BigInteger getDecimals() {
      return decimals;
    }

    public String getName() {
      return name;
    }

    public String getSymbol() {
      return symbol;
    }
  }
}
