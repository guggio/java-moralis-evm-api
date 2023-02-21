package io.moralis.evm.api.queryparams;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

class ListQueryParams<T> implements QueryParams {

  private final String key;
  private final List<T> values;
  private final Function<T, String> stringFromValueExtractor;

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
    StringJoiner stringJoiner = new StringJoiner("&");
    values.forEach(value -> stringJoiner.add(getKey() + "=" + stringFromValueExtractor.apply(value)));
    return stringJoiner.toString();
  }
}
