package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
public class DecodedTransaction {

  private String hash;

  private int nonce;

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

  private String from_address_label;

  private String to_address_label;

  private List<DecodedLog> logs;

  private DecodedEvent decoded_call;
}
