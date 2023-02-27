package io.moralis.evm.model;

import java.math.BigInteger;

@SuppressWarnings("unused")
public class Erc20Transaction {

  private String transaction_hash;

  private String address;

  private String block_timestamp;

  private long block_number;

  private String block_hash;

  private String to_address;

  private String from_address;

  private BigInteger value;

  private int transaction_index;

  private int log_index;

  public String getTransactionHash() {
    return transaction_hash;
  }

  public String getAddress() {
    return address;
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

  public String getToAddress() {
    return to_address;
  }

  public String getFromAddress() {
    return from_address;
  }

  public BigInteger getValue() {
    return value;
  }

  public int getTransactionIndex() {
    return transaction_index;
  }

  public int getLogIndex() {
    return log_index;
  }
}
