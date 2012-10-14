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
}

object MatchingUnit {
	def apply(linkedOrder: LinkedOrder) : MatchingUnit = {
		new MatchingUnit(linkedOrder.notional, linkedOrder, linkedOrder.linkedOrder)
	}
}
