package com.prystupa

import com.prystupa.Direction._
import com.prystupa.OrderType._

/**
 * Date: 10/13/12
 * Time: 10:42 PM
 */
class LinkedOrder private (o:Order)
	extends Order(o.id, o.price, o.notional, o.direction,
		o.instrument, o.orderType, o.party) {

	private var order:LinkedOrder = null;

	def linkedOrder:LinkedOrder = { order }
	private def setLinkedOrder(linkedOrder: LinkedOrder) { this.order = linkedOrder }

	/**
	 * Checks if this order is the same as other linked order
	 */

	def isSame(that:LinkedOrder):Boolean = {
		(that.id == id && that.linkedOrder.id == linkedOrder.id) ||
		(that.id == linkedOrder.id && that.linkedOrder.id == id)
	}

	/**
	 * Checks that economic attributes are the same
	 * Economic attirubutes exclude id and type
	 * @param that
	 * @return
	 */
	def isSameEconomics(that:LinkedOrder):Boolean = {
		(Order.isSameEconomics(this, that) && Order.isSameEconomics(that.linkedOrder, this.linkedOrder)) ||
			(Order.isSameEconomics(this, that.linkedOrder) && Order.isSameEconomics(that.linkedOrder, this))
	}
}

object LinkedOrder {
	def link(order1:Order, order2:Order):LinkedOrder = {
		require(canLink(order1, order2))
		val linkedOrder1 = new LinkedOrder(order1)
		val linkedOrder2 = new LinkedOrder(order2)
		linkedOrder1.setLinkedOrder(linkedOrder2)
		linkedOrder2.setLinkedOrder(linkedOrder1)
		linkedOrder1
	}

	/**
	 * Test if two orders can create a swap
	 * @param order1 - order to check
	 * @param order2 - order to check
	 * @return
	 */
	private def canLink(order1: Order, order2: Order):Boolean = {
		order1.instrument.symbol.equals(order2.instrument.symbol) &&
			order1.party.equals(order2.party) &&
			order1.direction == Direction.reverse(order2.direction) &&
			order1.party.equals(order2.party)  &&
			order1.orderType == order2.orderType
	}



}