package com.prystupa

import collection.mutable.ListBuffer


/**
 * Represents a chain of matched orders, along with corresponding notional
 *
 * Date: 10/7/12
 * Time: 5:07 PM
 */
class MatchingChain(val matchingUnits: List[MatchingUnit], val matchNotional: Long) {


	/**
	 * Represents matching result chain
	 * @param matchingUnit appends matching unit
	 * @return
	 */
	def append(matchingUnit: MatchingUnit): MatchingChain = {
		val newNotional = matchingUnit.notional
		val newMatchingUnits = matchingUnit :: matchingUnits
		new MatchingChain(newMatchingUnits, math.min(newNotional, matchNotional))
	}

	/**
	 * Checks if this matching chain already contains this order
	 * @param order
	 * @return
	 */
	def containsOrder(order: Order): Boolean = {
		matchingUnits.find(unit => unit.order1.id == order.id ||
			unit.order2.id == order.id) != None
	}

	/**
	 * Checks that economics of chain are the same - chain
	 * Exists of units with same economics
	 */
	def isSameEconomics(that:MatchingChain):Boolean = {
		var same:Boolean = true
		for (unit <- matchingUnits) {
			val matchFound = (that.matchingUnits.find(p => p.isSameEconomics(unit)) != None)
			same = same && matchFound
		}
		for (unit <- that.matchingUnits) {
			val matchFound = (matchingUnits.find(p => p.isSameEconomics(unit)) != None)
			same = same && matchFound
		}
		same
	}
}

object MatchingChain {
	val EMPTY_CHAIN = new MatchingChain(List(), 0)
}
