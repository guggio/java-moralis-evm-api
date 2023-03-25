package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
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

}
