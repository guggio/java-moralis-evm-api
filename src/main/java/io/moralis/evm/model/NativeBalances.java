package io.moralis.evm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
public class NativeBalances {

  private String chain;

  private String chain_id;

  private String total_balance;

  private long block_number;

  private String block_timestamp;

  private String total_balance_formatted;

  private List<WalletBalance> wallet_balances;

  public BigInteger getTotal_balance() {
    return total_balance.isBlank() ? BigInteger.ZERO : new BigInteger(total_balance);
  }

  public BigDecimal getTotal_balance_formatted() {
    return total_balance_formatted.isBlank() ? BigDecimal.ZERO : new BigDecimal(total_balance_formatted);
  }

  @Getter
  @Setter
  public static class WalletBalance {

    private String address;

    private String balance;

    private String balance_formatted;

    public BigInteger getBalance() {
      return balance.isBlank() ? BigInteger.ZERO : new BigInteger(balance);
    }

    public BigDecimal getBalance_formatted() {
      return balance_formatted.isBlank() ? BigDecimal.ZERO : new BigDecimal(balance_formatted);
    }
  }
}
