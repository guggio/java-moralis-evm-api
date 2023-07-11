package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@SuppressWarnings("squid:S00116")
public class Log {

  private int log_index;

  private String transaction_hash;

  private int transaction_index;

  private String address;

  private String data;

  private String topic0;

  @Nullable
  private String topic1;

  @Nullable
  private String topic2;

  @Nullable
  private String topic3;

  private String block_timestamp;

  private long block_number;

  private String block_hash;

}
