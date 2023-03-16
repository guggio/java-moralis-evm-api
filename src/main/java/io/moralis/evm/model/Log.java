package io.moralis.evm.model;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
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

  public int getLogIndex() {
    return log_index;
  }

  public String getTransactionHash() {
    return transaction_hash;
  }

  public int getTransactionIndex() {
    return transaction_index;
  }

  public String getAddress() {
    return address;
  }

  public String getData() {
    return data;
  }

  public String getTopic0() {
    return topic0;
  }

  @Nullable
  public String getTopic1() {
    return topic1;
  }

  @Nullable
  public String getTopic2() {
    return topic2;
  }

  @Nullable
  public String getTopic3() {
    return topic3;
  }

  public String getBlockTimestamp() {
    return block_timestamp;
  }

  public long getBlockNumber() {
    return block_number;
  }

  public String getBlockHash() {
    return block_hash;
  }
}
