package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.math.BigInteger;

@Getter
@Setter
public class Erc20Metadata {

  private String address;

  private String name;

  private String symbol;

  private String decimals;

  @Nullable
  private String logo;

  @Nullable
  private String logo_hash;

  @Nullable
  private String thumbnail;

  @Nullable
  private Long block_number;

  @Nullable
  private String created_at;

  @Nullable
  private Integer validated;

  private boolean possible_spam;

  public BigInteger getDecimals() {
    return decimals.isBlank() ? BigInteger.ZERO : new BigInteger(decimals);
  }
}
