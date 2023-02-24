package io.moralis.evm.core;

public enum Chain {
  ETH, POLYGON, BSC;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
