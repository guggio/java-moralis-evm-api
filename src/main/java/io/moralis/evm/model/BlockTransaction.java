package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("squid:S00116")
public class BlockTransaction extends BaseTransaction {

  private List<Log> logs;

  private List<InternalTransaction> internal_transactions;

}
