package io.moralis.evm.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockHashTest {

  @Test
  void shouldPassValidation() {
    String hash = "0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef";
    BlockHash blockHash = BlockHash.of(hash);
    assertEquals(hash, blockHash.getHash());
  }

  @Test
  void shouldThrowWithNullString() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> BlockHash.of(null));
    assertEquals("The provided hash null does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWithEmptyString() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> BlockHash.of(""));
    assertEquals("The provided hash  does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWithout0xStart() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> BlockHash.of("422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef00"));
    assertEquals("The provided hash 422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef00 does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWith63Characters() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> BlockHash.of("0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0e"));
    assertEquals("The provided hash 0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0e does not match the required format!", exception.getMessage());
  }

  @Test
  void shouldThrowWith65Characters() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> BlockHash.of("0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef0"));
    assertEquals("The provided hash 0x422446d05ab519b81f2276ba28eea5e629769c6e0d6f7448328b813e2f18e0ef0 does not match the required format!", exception.getMessage());
  }
}