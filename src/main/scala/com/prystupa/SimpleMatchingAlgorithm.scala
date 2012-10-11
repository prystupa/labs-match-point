package com.prystupa

/**
 * Date: 10/10/12
 * Time: 11:01 PM
 */
class SimpleMatchingAlgorithm extends MatchingAlgorithm {
	/**
	 * For simplicity is set to 0
	 */
	def matchingCost(order1: Order, order2: Order): BigDecimal = { BigDecimal.apply(0) }

	/**
	 * Orders match of the same tenor, same instrument, different direction, same price
	 */
	def canMatch(order1: Order, order2: Order, matchingChain: MatchingChain): Boolean = {
		true
	}

	/**
	 * Creates a match given two order and notional
	 * @param order1 - first order
	 * @param order2 - second order
	 * @param notional - notional of order
	 * @return - returns constructed matching unit
	 */
	def createMatch(order1: Order, order2: Order, notional: Int) = null

	/**
	 * Creates a swap given two order and notional
	 * @param order1 - first order
	 * @param order2 - second order
	 * @param notional - notional of order
	 * @return - returns constructed matching unit
	 */
	def createSwap(order1: Order, order2: Order, notional: Int) = null

	/**
	 * Returns all possible orders that can createActive a swap with the given order
	 * @param order - one leg of swap
	 * @param counterpartyOders - all counterparty orders
	 * @return returns a subset of orders that can be swap matches for a given order book
	 */
	def getSwapMatches(order: Order, counterpartyOders: OrderBook) = null
}
