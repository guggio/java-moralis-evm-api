package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("squid:S00116")
public class DecodedTransaction extends BaseTransaction {

  private String from_address_label;

  private String to_address_label;

  private List<DecodedLog> logs;

  private DecodedEvent decoded_call;
}
