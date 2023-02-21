package io.moralis.evm.core;

public enum Chain {
  ETH, POLYGON;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
