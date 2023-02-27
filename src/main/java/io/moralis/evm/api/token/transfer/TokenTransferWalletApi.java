package io.moralis.evm.api.token.transfer;

import io.moralis.evm.api.GetApi;
import io.moralis.evm.core.Chain;
import io.moralis.evm.model.Erc20TransactionCollection;

import java.time.LocalDateTime;

public interface TokenTransferWalletApi extends GetApi<Erc20TransactionCollection> {

  /**
   * Add the chain to the query
   *
   * @param chain chain to be queried
   * @return TokenTransferWalletApi with the chain added as query param
   */
  TokenTransferWalletApi chain(Chain chain);

  /**
   * The minimum block number from which to get the transactions
   * Provide the param 'from_block' or 'from_date'
   * If 'from_date' and 'from_block' are provided, 'from_block' will be used.
   *
   * @param blockNumber minimum block number from which to get the transactions
   * @return TokenTransferWalletApi with the fromBlock added as query param
   */
  TokenTransferWalletApi fromBlock(long blockNumber);

  /**
   * The maximum block number from which to get the transactions.
   * Provide the param 'to_block' or 'to_date'
   * If 'to_date' and 'to_block' are provided, 'to_block' will be used.
   *
   * @param blockNumber maximum block number from which to get the transactions
   * @return TokenTransferWalletApi with the toBlock added as query param
   */
  TokenTransferWalletApi toBlock(long blockNumber);

  /**
   * The start date from which to get the transactions
   * Provide the param 'from_block' or 'from_date'
   * If 'from_date' and 'from_block' are provided, 'from_block' will be used.
   *
   * @param fromDate start date from which to get the transactions
   * @return TokenTransferWalletApi with the fromDate added as query param
   */
  TokenTransferWalletApi fromDate(LocalDateTime fromDate);

  /**
   * Get the transactions up to this date
   * Provide the param 'to_block' or 'to_date'
   * If 'to_date' and 'to_block' are provided, 'to_block' will be used.
   *
   * @param toDate final date to get the transactions
   * @return TokenTransferWalletApi with the toDate added as query param
   */
  TokenTransferWalletApi toDate(LocalDateTime toDate);

  /**
   * The desired page size of the result.
   *
   * @param pageSize desired page size
   * @return TokenTransferWalletApi with the pageSize added as query param
   */
  TokenTransferWalletApi pageSize(int pageSize);


  /**
   * If the result should skip returning the total count (Improves performance).
   *
   * @param disableTotal setting to disable the total calculation for the query
   * @return TokenTransferWalletApi with the disableTotal added as query param
   */
  TokenTransferWalletApi disableTotal(boolean disableTotal);

  /**
   * The cursor returned in the previous response (used for getting the next page).
   *
   * @param cursor cursor from the last page to continue looping
   * @return TokenTransferWalletApi with the disableTotal added as query param
   */
  TokenTransferWalletApi cursor(String cursor);


}
