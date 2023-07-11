package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("squid:S00116")
public class Transaction extends BaseTransaction {

  private List<InternalTransaction> internal_transactions = new ArrayList<>();
}
