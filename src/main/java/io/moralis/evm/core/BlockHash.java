package io.moralis.evm.core;

import java.util.regex.Pattern;

public class BlockHash {

  private static final Pattern BLOCK_HASH_PATTERN = Pattern.compile("0x[a-zA-Z0-9]{64}");

  private final String hash;

  private BlockHash(String hash) {
    this.hash = hash;
  }

  public static BlockHash of(String hash) {
    if (!isBlockHash(hash)) {
      throw new IllegalArgumentException(String.format("The provided hash %s does not match the required format!", hash));
    }
    return new BlockHash(hash);
  }

  private static boolean isBlockHash(String hash) {
    return hash != null
        && BLOCK_HASH_PATTERN.matcher(hash).matches();
  }

  public String getHash() {
    return hash;
  }
}
