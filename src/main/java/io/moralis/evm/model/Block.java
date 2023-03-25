package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
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

}
