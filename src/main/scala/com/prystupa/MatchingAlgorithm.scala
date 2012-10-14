package com.prystupa

/**
 * Date: 10/7/12
 * Time: 4:53 PM
 * Encapsulates matching algorithm
 */
trait MatchingAlgorithm {
	/**
	 * Encapsulates matching cost between two orders,
	 * Order chains can be ranked based on cost
	 * @param order1 - order to determine cost
	 * @param order2 - order to determine cost
	 * @return
	 */
	def matchingCost(order1: Order, order2: Order): BigDecimal


	/**
	 * Test if two orders can be matched based on matching algorithm
	 * @param order1 - order to check for match
	 * @param order2 - order to check for match
	 * @param matchingChain represents a chain of orders that have been matched up untils this point
	 * @return
	 */
	def canMatch(order1: Order, order2: Order, matchingChain: MatchingChain): Boolean


	/**
	 * Test if two orders can create a swap
	 * @param order1 - order to check
	 * @param order2 - order to check
	 * @param matchingChain represents a chain of orders that have been matched up untils this point
	 * @return
	 */
	def canSwap(order1: Order, order2: Order, matchingChain: MatchingChain): Boolean


	/**
	 * Creates a match given two order and notional
	 * @param order1 - first order
	 * @param order2 - second order
	 * @param notional - notional of order
	 * @return - returns constructed matching unit
	 */
	def createMatch(order1: Order, order2: Order, notional: Option[Long]): MatchingUnit


	/**
	 * Creates a swap given two order and notional
	 * @param order1 - first order
	 * @param order2 - second order
	 * @param notional - notional of order
	 * @return - returns constructed matching unit
	 */
	def createSwap(order1: Order, order2: Order, notional: Option[Long]): MatchingUnit

}
