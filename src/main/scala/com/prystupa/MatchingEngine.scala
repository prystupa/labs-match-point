package com.prystupa

import java.util
import collection._

/**
 * Date: 10/7/12
 * Time: 7:55 PM
 */
class MatchingEngine(orderBook: OrderBook) {

	val matchingAlgorithm = new SimpleMatchingAlgorithm

	def createMatch(): mutable.Map[Int, MatchingResult] = {
		val activeBook = orderBook.getByOrderType(OrderType.ACTIVE)
		val ret = mutable.HashMap.empty[Int, MatchingResult]
		for (activeOrder <- activeBook.getOrders) {
			ret.put(activeOrder.id, dfsMatch(matchingAlgorithm, activeOrder))
		}
		ret
	}

	/**
	 * Tries to find all matches in a graph using DFS search
	 * NOTE: This will cross your own orders with no particular preference
	 * @param matchingAlgorithm - matching algorithm to use
	 * @param originatingSwap - swap to match
	 * @return
	 */
	def dfsMatch(matchingAlgorithm: MatchingAlgorithm, originatingSwap: LinkedOrder): MatchingResult = {
		val bookBySymbol = orderBook.getBySymbol(originatingSwap.instrument.symbol)
		val matchingChain = MatchingChain.EMPTY_CHAIN
		val matchingResult = new MatchingResult(new util.ArrayList())
		def dfsMatchHelper(swap: LinkedOrder, matchingChain: MatchingChain) {
			//If we reached end of chain, add to matching result
			if (swap.isSame(originatingSwap) && !matchingChain.matchingUnits.isEmpty) {
				matchingResult.matchingChains.add(matchingChain)
				//Else do dfs to find match
			} else {
				//Find all matches for a matching unit leg
				val orderToMatch = swap
				val bookByTenor = bookBySymbol.getByTenor(orderToMatch.instrument.tenor)
				val bookByTenorAndDirection = bookByTenor.getByDirection(Direction.reverse(orderToMatch.direction))
				//Iterate over all orders, check which orders can match
				val orders = bookByTenorAndDirection.getOrders
				for (cpOrder <- orders) {
					//If we can match, order with next order
					if (matchingAlgorithm.canMatch(orderToMatch, cpOrder, matchingChain)) {
						//We can match, createActive match
						val matchingUnit = matchingAlgorithm.createMatch(orderToMatch, cpOrder, Some(matchingChain.matchNotional))
						//Add to our chain
						val chainAndMatch = matchingChain.append(matchingUnit)
						//Traverse down
						dfsMatchHelper(cpOrder.linkedOrder, chainAndMatch)
					}
				}
			}
		}
		//Call helper
		//reverse for first order
		dfsMatchHelper(originatingSwap, matchingChain)
		//Return matching result
		matchingResult
	}
}
