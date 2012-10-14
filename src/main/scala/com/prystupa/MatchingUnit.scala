package com.prystupa

/**
 *
 * Represent a matching unit of an engine
 *
 *
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 10/7/12
 * Time: 1:44 PM
 */
class MatchingUnit(val notional: Long, val order1: Order, val order2: Order) {

	def flip():MatchingUnit = {
		new MatchingUnit(notional, order2, order1)
	}

	def isSame(that:MatchingUnit) = {
		that.notional == notional && ((that.order1.id == order1.id && that.order2.id == order2.id) ||
			(that.order2.id == order1.id && that.order1.id == order2.id))
	}

	/**
	 * Check if 'that' matching unit represents same match
	 * as this matching unit
	 * @param that - other matching unit
	 * @return
	 */
	def isSameEconomics(that:MatchingUnit):Boolean = {
		notional == that.notional &&
		((Order.isSameEconomics(order1, that.order1) && Order.isSameEconomics(order2, that.order2)) ||
			(Order.isSameEconomics(order1, that.order2) && Order.isSameEconomics(order2, that.order1)))
	}

}

object MatchingUnit {
	def apply(linkedOrder: LinkedOrder) : MatchingUnit = {
		new MatchingUnit(linkedOrder.notional, linkedOrder, linkedOrder.linkedOrder)
	}
}
