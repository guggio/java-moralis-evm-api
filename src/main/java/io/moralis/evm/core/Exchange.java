package io.moralis.evm.core;

public enum Exchange {
  UNISWAP_V2, UNISWAP_V3, SUSHISWAP_V2, PANCAKESWAP_V1, PANCAKESWAP_V2, QUICKSWAP;

  @Override
  public String toString() {
    return super.toString().toLowerCase().replace("_", "-");
  }
}
