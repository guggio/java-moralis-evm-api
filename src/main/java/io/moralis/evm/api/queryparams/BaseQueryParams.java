package io.moralis.evm.api.queryparams;

import java.util.function.Function;

class BaseQueryParams<T> implements QueryParams {

  private final String key;
  private final T value;
  private final Function<T, String> stringFromValueExtractor;

  BaseQueryParams(String key, T value) {
    this(key, value, T::toString);
  }

  public BaseQueryParams(String key, T value, Function<T, String> stringFromValueExtractor) {
    this.key = key;
    this.value = value;
    this.stringFromValueExtractor = stringFromValueExtractor;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getParams() {
    return key + "=" + stringFromValueExtractor.apply(value);
  }
}
