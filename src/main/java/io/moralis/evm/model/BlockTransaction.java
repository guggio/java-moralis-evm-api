package io.moralis.evm.model;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.List;

@SuppressWarnings("unused")
public class BlockTransaction {

  private String hash;

  private String nonce;

  private int transaction_index;

  private String from_address;

  private String to_address;

  private BigInteger value;

  private BigInteger gas;

  private BigInteger gas_price;

  private String input;

  private BigInteger receipt_cumulative_gas_used;

  private BigInteger receipt_gas_used;

  @Nullable
  private String receipt_contract_address;

  @Nullable
  private String receipt_root;

  private int receipt_status;

  private String block_timestamp;

  private long block_number;

  private String block_hash;

  private List<Log> logs;

  private List<InternalTransaction> internal_transactions;

  public String getHash() {
    return hash;
  }

  public String getNonce() {
    return nonce;
  }

  public int getTransactionIndex() {
    return transaction_index;
  }

  public String getFromAddress() {
    return from_address;
  }

  public String getToAddress() {
    return to_address;
  }

  public BigInteger getValue() {
    return value;
  }

  public BigInteger getGas() {
    return gas;
  }

  public BigInteger getGasPrice() {
    return gas_price;
  }

  public String getInput() {
    return input;
  }

  public BigInteger getReceiptCumulativeGasUsed() {
    return receipt_cumulative_gas_used;
  }

  public BigInteger getReceiptGasUsed() {
    return receipt_gas_used;
  }

  @Nullable
  public String getReceiptContractAddress() {
    return receipt_contract_address;
  }

  @Nullable
  public String getReceiptRoot() {
    return receipt_root;
  }

  public int getReceiptStatus() {
    return receipt_status;
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

  public List<Log> getLogs() {
    return logs;
  }

  public List<InternalTransaction> getInternalTransactions() {
    return internal_transactions;
  }
}
