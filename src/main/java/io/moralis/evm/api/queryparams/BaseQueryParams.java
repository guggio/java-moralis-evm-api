package io.moralis.evm.api.queryparams;

class BaseQueryParams<T> implements QueryParams {

  private final String key;
  private final T value;

  BaseQueryParams(String key, T value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getParams() {
    return key + "=" + value;
  }
}
