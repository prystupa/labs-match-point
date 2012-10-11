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
	 * @param originatingSwap - unit to match
	 * @return
	 */
	def dfsMatch(matchingAlgorithm: MatchingAlgorithm, originatingSwap: MatchingUnit): MatchingResult = {
		val bookBySymbol = orderBook.getBySymbol(originatingSwap.order1.instrument.symbol)
		val matchingChain = MatchingChain.EMPTY_CHAIN.append(originatingSwap)
		val matchingResult = new MatchingResult(new util.ArrayList())
		def dfsMatchHelper(swap: MatchingUnit, matchingChain: MatchingChain) {
			//If we reached end of chain, add to matching result
			if (swap.order2 == originatingSwap.order1) {
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
						val matchingUnit = matchingAlgorithm.createMatch(order2, cpOrder1, swap.notional)
						//Add to our chain
						val chainAndTrade = matchingChain.append(matchingUnit)
						//Find all outgoing orders for this counterparty with later tenor date
						val counterpartyBook = bookBySymbol.getByParty(cpOrder1.party)
						//Find all orders for order counterparty which satisfy algorithm
						for (cpOrder2 <- counterpartyBook) {
							if (matchingAlgorithm.canSwap(cpOrder1, cpOrder2, matchingChain)) {
								//Create swap
								val newSwap = matchingAlgorithm.createSwap(cpOrder1, cpOrder2, matchingUnit.notional)
								//traverse down
								dfsMatchHelper(newSwap, chainAndTrade.append(newSwap))
							}
						}
					}
				}
			}
		}
		//Call helper
		dfsMatchHelper(originatingSwap, matchingChain)
		//Return matching result
		matchingResult
	}
}
