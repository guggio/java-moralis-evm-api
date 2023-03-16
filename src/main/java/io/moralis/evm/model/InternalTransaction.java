package io.moralis.evm.model;

import java.math.BigInteger;

@SuppressWarnings("unused")
public class InternalTransaction {

  private String transaction_hash;

  private long block_number;

  private String block_hash;

  private String type;

  private String from;

  private String to;

  private BigInteger value;

  private BigInteger gas;

  private BigInteger gas_used;

  private String input;

  private String output;

  public String getTransactionHash() {
    return transaction_hash;
  }

  public long getBlockNumber() {
    return block_number;
  }

  public String getBlockHash() {
    return block_hash;
  }

  public String getType() {
    return type;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public BigInteger getValue() {
    return value;
  }

  public BigInteger getGas() {
    return gas;
  }

  public BigInteger getGasUsed() {
    return gas_used;
  }

  public String getInput() {
    return input;
  }

  public String getOutput() {
    return output;
  }
}
