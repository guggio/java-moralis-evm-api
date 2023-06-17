package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DecodedEvent {

  private String signature;

  private String label;

  private String type;

  private List<EventParam> params;
}
