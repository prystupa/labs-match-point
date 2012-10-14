package com.prystupa

import java.util

/**
 * Date: 10/7/12
 * Time: 7:55 PM
 */
class MatchingEngine(orderBook: OrderBook) {

	/**
	 * Tries to find all matches in a graph using DFS search
	 * NOTE: This will cross your own orders with no particular preference
	 * @param matchingAlgorithm - matching algorithm to use
	 * @param originatingSwap - swap to match
	 * @return
	 */
	def dfsMatch(matchingAlgorithm: MatchingAlgorithm, originatingSwap: LinkedOrder): MatchingResult = {
		val bookBySymbol = orderBook.getBySymbol(originatingSwap.instrument.symbol)
		val originatingMatchingUnit = MatchingUnit.apply(originatingSwap)
		val matchingChain = MatchingChain.EMPTY_CHAIN.append(originatingMatchingUnit)
		val matchingResult = new MatchingResult(new util.ArrayList())
		def dfsMatchHelper(swap: MatchingUnit, matchingChain: MatchingChain) {
			//If we reached end of chain, add to matching result
			if (matchingAlgorithm.canMatch(swap.order2, originatingMatchingUnit.order2, matchingChain)) {
				matchingResult.matchingChains.add(matchingChain)
				//Else do dfs to find match
			} else {
				//Find all matches for order2, this is outgoing leg
				val order2 = swap.order2
				val bookByTenor = bookBySymbol.getByTenor(order2.instrument.tenor)
				val bookByTenorAndDirection = bookByTenor.getByDirection(Direction.reverse(order2.direction))
				//Iterate over all orders, check which orders can match
				val orders = bookByTenorAndDirection.getOrders
				for (cpOrder1 <- orders) {
					//If we can match, order with next order
					if (matchingAlgorithm.canMatch(order2, cpOrder1, matchingChain)) {
						//We can match, createActive match
						val matchingUnit = matchingAlgorithm.createMatch(order2, cpOrder1, Some(swap.notional))
						//Add to our chain
						val chainAndTrade = matchingChain.append(matchingUnit)
						//create new swap
						val newSwap = MatchingUnit.apply(cpOrder1)
						//Traverse down
						dfsMatchHelper(newSwap, chainAndTrade.append(newSwap))
					}
				}
			}
		}
		//Call helper
		//reverse for first order
		dfsMatchHelper(originatingMatchingUnit, matchingChain)
		//Return matching result
		matchingResult
	}
}
