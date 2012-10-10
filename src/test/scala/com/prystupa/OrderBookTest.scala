package com.prystupa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.junit.Test
import org.scalatest.FunSuite

/**
 * Date: 10/9/12
 * Time: 10:13 PM
 */
@RunWith(classOf[JUnitRunner])
class OrderBookTest extends FunSuite {
	test("add order") {
		val book = OrderBook.createImmutable
		val order = Order.createBuy("A")("USDEUR", "5D", 5, 10)
		val newBook = book.addOrder(order)
		assert(1 == newBook.getOrders.count(o => o.id == order.id))
	}
}
