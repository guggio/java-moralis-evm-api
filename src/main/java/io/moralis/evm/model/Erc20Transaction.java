package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@SuppressWarnings("squid:S00116")
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

}
