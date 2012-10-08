package com.prystupa

/**
 * Date: 10/7/12
 * Time: 7:55 PM
 */
class MatchingEngine(orderBook: OrderBook) {

  /**
   * Tries to find all matches in a graph using DFS search
   * NOTE: This will cross your own orders with no particular preference
   * @param matchingAlgo - matching algorithm to use
   * @param firstMatchingUnit - unit to match
   * @return
   */
  def dfsMatch(matchingAlgo : MatchingAlgorithm, firstMatchingUnit: MatchingUnit) : MatchingResult = {
      val bookBySymbol = orderBook.getBySymbol(firstMatchingUnit.order1.instrument.symbol)
      var matchingChain = MatchingChain.EMPTY_CHAIN.append(firstMatchingUnit)
      def dfsMatchHelper(matchingUnit: MatchingUnit, matchingChain: MatchingChain):MatchingResult = {
        //If we reached end of chain, add to matching resule
        if (matchingUnit.order2 == firstMatchingUnit.order1) {
        //Else do dfs to find match
        } else {
          //Find all matches for order1
          val order1 = matchingUnit.order1
          val order2 = matchingUnit.order2
          val bookByTenor = bookBySymbol.getByTenor(order1.instrument.tenor)
          val bookByDirection = bookByTenor.getByDirection(Direction.reverse(order1.direction))
          //Iterate over all orders, check which orders can match
          val orders = bookByDirection.getOrders
          for (order <- orders) {
            //If we can match,
            if (matchingAlgo.canMatch(order1, order, matchingChain)) {
              //Find all orders for order counterparty which has
              //Opposite direction of order and whose tenor > order tennor
              //and recurse down using order and this new order
            }
          }
        }
      }
      null
  }
}
