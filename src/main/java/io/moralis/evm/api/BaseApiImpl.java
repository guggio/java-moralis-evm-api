package io.moralis.evm.api;

public abstract class BaseApiImpl implements BaseApi {

  private final BaseApi wrappedApi;

  protected BaseApiImpl(BaseApi wrappedApi) {
    this.wrappedApi = wrappedApi;
  }

  @Override
  public final String getApiKey() {
    return wrappedApi.getApiKey();
  }

  @Override
  public final String buildUrl() {
    return wrappedApi.buildUrl() + getUrlPath();
  }

  protected abstract String getUrlPath();
}
