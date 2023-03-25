package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
public class BlockByDate {

  private String date;

  private long block;

  private long timestamp;

  @Nullable
  private String block_timestamp;

  @Nullable
  private String hash;

  @Nullable
  private String parent_hash;

}
