package io.moralis.evm.model;

import java.util.List;

@SuppressWarnings("unused")
public class Erc20TransactionCollection {

  // Nullable, if disableTotal was set
  private Integer total;

  private int page;

  private int page_size;

  // Nullable with only one page of data
  private String cursor;

  private List<Erc20Transaction> result;

  public Integer getTotal() {
    return total;
  }

  public int getPage() {
    return page;
  }

  public int getPageSize() {
    return page_size;
  }

  public String getCursor() {
    return cursor;
  }

  public List<Erc20Transaction> getResult() {
    return result;
  }

}
