package io.moralis.evm.core;

import java.util.regex.Pattern;

public class ValidatedAddress {

  private static final Pattern ADDRESS_PATTERN = Pattern.compile("0x[a-zA-Z0-9]{40}");

  private final String address;

  private ValidatedAddress(String address) {
    this.address = address;
  }

  public static ValidatedAddress of(String address) {
    if (!isAddress(address)) {
      throw new IllegalArgumentException(String.format("The provided address %s does not match the required format!", address));
    }
    return new ValidatedAddress(address);
  }

  private static boolean isAddress(String address) {
    return address != null
        && ADDRESS_PATTERN.matcher(address).matches();
  }

  public String getAddress() {
    return address;
  }
}
