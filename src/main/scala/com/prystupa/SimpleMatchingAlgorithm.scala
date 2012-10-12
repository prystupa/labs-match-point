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
	 * Orders match of the same tenor, same instrument, different direction, same price, different party
	 */
	def canMatch(order1: Order, order2: Order, matchingChain: MatchingChain):Boolean = {
		order1.instrument.symbol.equals(order2.instrument.symbol) &&
		order1.instrument.tenor.equals(order2.instrument.tenor) &&
		order1.direction == Direction.reverse(order2.direction) &&
		!order1.party.equals(order2.party) &&
		order1.price.equals(order2.price)
	}

	/**
	 * Creates a match given two order and notional
	 * @param order1 - first order
	 * @param order2 - second order
	 * @param notional - notional of order
	 * @return - returns constructed matching unit
	 */
	def createMatch(order1: Order, order2: Order, notional: Option[Int]):MatchingUnit = {
		val minOrder = math.min(order1.notional, order2.notional)
		val existingNotional = notional.getOrElse(math.min(order1.notional, order2.notional))
		new MatchingUnit(math.min(minOrder, existingNotional), order1, order2)
	}

	/**
	 * Creates a swap given two order and notional
	 * @param order1 - first order
	 * @param order2 - second order
	 * @param notional - notional of order
	 * @return - returns constructed matching unit
	 */
	def createSwap(order1: Order, order2: Order, notional: Option[Int]):MatchingUnit = {
		val minOrder = math.min(order1.notional, order2.notional)
		val existingNotional = notional.getOrElse(math.min(order1.notional, order2.notional))
		new MatchingUnit(math.min(minOrder, existingNotional), order1, order2)
	}

	/**
	 * Test if two orders can create a swap
	 * @param order1 - order to check
	 * @param order2 - order to check
	 * @param matchingChain represents a chain of orders that have been matched up untils this point
	 * @return
	 */
	def canSwap(order1: Order, order2: Order, matchingChain: MatchingChain):Boolean = {
		order1.instrument.symbol.equals(order2.instrument.symbol) &&
		order1.party.equals(order2.party) &&
		order1.direction == Direction.reverse(order2.direction) &&
		order1.party.equals(order2.party) &&
		CalendarUtils.tenorAfter(order2.instrument.tenor, order1.instrument.tenor)
	}
}
