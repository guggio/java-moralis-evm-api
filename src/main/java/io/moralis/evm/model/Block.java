package io.moralis.evm.model;

import java.math.BigInteger;
import java.util.List;

@SuppressWarnings("unused")
public class Block {

  private String timestamp;

  private long number;

  private String hash;

  private String parent_hash;

  private String nonce;

  private String sha3_uncles;

  private String logs_bloom;

  private String transactions_root;

  private String state_root;

  private String receipts_root;

  private String miner;

  private String difficulty;

  private String total_difficulty;

  private String size;

  private String extra_data;

  private BigInteger gas_limit;

  private BigInteger gas_used;

  private int transaction_count;

  private List<BlockTransaction> transactions;

  public String getTimestamp() {
    return timestamp;
  }

  public long getNumber() {
    return number;
  }

  public String getHash() {
    return hash;
  }

  public String getParentHash() {
    return parent_hash;
  }

  public String getNonce() {
    return nonce;
  }

  public String getSha3Uncles() {
    return sha3_uncles;
  }

  public String getLogsBloom() {
    return logs_bloom;
  }

  public String getTransactionsRoot() {
    return transactions_root;
  }

  public String getStateRoot() {
    return state_root;
  }

  public String getReceiptsRoot() {
    return receipts_root;
  }

  public String getMiner() {
    return miner;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public String getTotalDifficulty() {
    return total_difficulty;
  }

  public String getSize() {
    return size;
  }

  public String getExtraData() {
    return extra_data;
  }

  public BigInteger getGasLimit() {
    return gas_limit;
  }

  public BigInteger getGasUsed() {
    return gas_used;
  }

  public int getTransactionCount() {
    return transaction_count;
  }

  public List<BlockTransaction> getTransactions() {
    return transactions;
  }
}
