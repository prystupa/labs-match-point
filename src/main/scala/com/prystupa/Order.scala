package com.prystupa

import Direction._
import OrderType._
import java.util.concurrent.atomic.AtomicInteger

/**
 * Date: 10/7/12
 * Time: 4:32 PM
 *
 * Represents order object with the following attributes
 * id - order identifier
 * price - price at which order was submited
 * notional - Size of the order
 * direction - either buy or sell
 * instrument - code for a trading instrument. This is a simplification,
 * usually this will be an id of a generated instrument
 * order type - either standing or active
 * party - Party that requested order. This is a simplification
 * This class should be immutable
 */
class Order(val id:Int, val price: BigDecimal,
            val notional: Long, val direction: Direction,
            val instrument: Instrument, val orderType: OrderType,
            val party: String ) {
	/**
	 * Checks if given order has same economics as other order.
	 * Comparison excludes orderType and id
	 * @param that - order to compare to
	 * @return
	 */
	def isSameEconomics(that:Order):Boolean = {
   	    Order.isSameEconomics(this, that)
	}


}

object Order {
	private val id = new AtomicInteger(0)

	def create(orderType: OrderType)(direction: Direction)(party: String)(symbol: String, tenor: String, price:String, notional: Long): Order = {
		new Order(id.getAndIncrement, BigDecimal.apply(price), notional,
			direction, new Instrument(tenor, symbol), orderType, party)
	}

	def isSameEconomics(that: Order, thisOne: Order): Boolean = {
		thisOne.price.equals(that.price) &&
			thisOne.notional == that.notional &&
			thisOne.direction == that.direction &&
			thisOne.instrument.symbol.equals(that.instrument.symbol) &&
			thisOne.instrument.tenor.equals(that.instrument.tenor) &&
			thisOne.party.equals(that.party)
	}

	val createActiveBuy = create(OrderType.ACTIVE)(Direction.BUY) _
	val createActiveSell = create(OrderType.ACTIVE)(Direction.SELL) _
}
