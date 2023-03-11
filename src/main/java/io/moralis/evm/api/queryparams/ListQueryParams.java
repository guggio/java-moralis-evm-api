package io.moralis.evm.api.queryparams;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class ListQueryParams<T> implements QueryParams {

  private final String key;
  private final List<T> values;
  private final Function<T, String> stringFromValueExtractor;

  ListQueryParams(String key, List<T> values) {
    this(key, values, T::toString);
  }

  ListQueryParams(String key, List<T> values, Function<T, String> stringFromValueExtractor) {
    this.key = key;
    this.values = values;
    this.stringFromValueExtractor = stringFromValueExtractor;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getParams() {
    return values.stream()
        .map(value -> getKey() + "=" + stringFromValueExtractor.apply(value))
        .collect(Collectors.joining("&"));
  }
}
