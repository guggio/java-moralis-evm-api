package io.moralis.evm.api.exception;

public class ConnectionException extends RuntimeException {

  private final int statusCode;
  private final ApiErrorMessage apiErrorMessage;

  public ConnectionException(int statusCode, ApiErrorMessage apiErrorMessage) {
    super(String.format("Request failed with status code %d and message: %s", statusCode, apiErrorMessage.getMessage()));
    this.statusCode = statusCode;
    this.apiErrorMessage = apiErrorMessage;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public ApiErrorMessage getApiErrorMessage() {
    return apiErrorMessage;
  }
}
