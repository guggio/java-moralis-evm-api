package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class TokenPrice {

  private NativePrice nativePrice;

  private BigDecimal usdPrice;

  @Nullable
  private String exchangeAddress;

  @Nullable
  private String exchangeName;

  @Getter
  @Setter
  public static class NativePrice {

    private BigInteger value;

    private BigInteger decimals;

    private String name;

    private String symbol;

  }
}
