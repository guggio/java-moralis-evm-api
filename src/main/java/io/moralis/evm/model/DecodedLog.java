package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("squid:S00116")
public class DecodedLog extends Log {

  private DecodedEvent decoded_event;

}
