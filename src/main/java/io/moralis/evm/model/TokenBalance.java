package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.math.BigInteger;

@Getter
@Setter
@SuppressWarnings("squid:S00116")
public class TokenBalance {

  private String token_address;

  private String name;

  private String symbol;

  @Nullable
  private String logo;

  @Nullable
  private String thumbnail;

  private BigInteger decimals;

  private BigInteger balance;

  private boolean possible_spam;

}
