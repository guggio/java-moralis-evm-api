package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
public class TransactionCollection {

  @Nullable
  private Integer total;

  private int page;

  private int page_size;

  @Nullable
  private String cursor;

  private List<Transaction> result;
}
