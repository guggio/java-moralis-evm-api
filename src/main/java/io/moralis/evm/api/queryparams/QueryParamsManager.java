package io.moralis.evm.api.queryparams;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class QueryParamsManager {

  private final Map<String, QueryParams> queryParamsMap;

  public QueryParamsManager() {
    queryParamsMap = new LinkedHashMap<>();
  }

  public String getParams() {
    if (queryParamsMap.isEmpty()) {
      return "";
    }
    StringJoiner stringJoiner = new StringJoiner("&", "?", "");
    queryParamsMap.values().forEach(queryParams -> stringJoiner.add(queryParams.getParams()));
    return stringJoiner.toString();
  }

  public void add(QueryParams queryParams) {
    queryParamsMap.put(queryParams.getKey(), queryParams);
  }
}
