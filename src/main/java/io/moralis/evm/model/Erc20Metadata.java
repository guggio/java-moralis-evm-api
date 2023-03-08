package io.moralis.evm.model;

import javax.annotation.Nullable;
import java.math.BigInteger;

@SuppressWarnings("unused")
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

  public String getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigInteger getDecimals() {
    return decimals.isBlank() ? BigInteger.ZERO : new BigInteger(decimals);
  }

  @Nullable
  public String getLogo() {
    return logo;
  }

  public String getLogoHash() {
    return logo_hash;
  }

  @Nullable
  public String getThumbnail() {
    return thumbnail;
  }

  @Nullable
  public Long getBlockNumber() {
    return block_number;
  }

  @Nullable
  public String getCreatedAt() {
    return created_at;
  }

  @Nullable
  public Integer getValidated() {
    return validated;
  }
}
