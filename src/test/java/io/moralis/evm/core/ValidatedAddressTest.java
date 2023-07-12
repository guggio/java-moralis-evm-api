package io.moralis.evm.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatedAddressTest {

  @Test
  void shouldPassValidation() {
    String addressString = "0xa2B13834161fD407218cf642C2D17060b26aeA09";
    ValidatedAddress validatedAddress = ValidatedAddress.of(addressString);
    assertEquals(addressString, validatedAddress.getAddress());
  }

  @Test
  void shouldThrowWithNullAddress() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidatedAddress.of(null));
    assertEquals("The provided address null does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWithEmptyString() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidatedAddress.of(""));
    assertEquals("The provided address  does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWithout0xStart() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidatedAddress.of("1xa2B13834161fD407218cf642C2D17060b26aeA09"));
    assertEquals("The provided address 1xa2B13834161fD407218cf642C2D17060b26aeA09 does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWith39Characters() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidatedAddress.of("0xa2B13834161fD407218cf642C2D17060b26aeA0"));
    assertEquals("The provided address 0xa2B13834161fD407218cf642C2D17060b26aeA0 does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWith41Characters() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidatedAddress.of("0xa2B13834161fD407218cf642C2D17060b26aeA09z"));
    assertEquals("The provided address 0xa2B13834161fD407218cf642C2D17060b26aeA09z does not match the required format!", exception.getMessage());
  }
}
