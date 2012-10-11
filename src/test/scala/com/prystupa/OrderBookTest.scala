package com.prystupa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.junit.Test
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Date: 10/9/12
 * Time: 10:13 PM
 */
@RunWith(classOf[JUnitRunner])
class OrderBookTest extends FunSuite with BeforeAndAfterEach {

	var book:OrderBook = _
	var orderA:Order = _
	var orderB:Order = _

	override def beforeEach() {
		val newBook = OrderBook.createImmutable
		orderA = Order.createActiveBuy("A")("USDEUR", "5D", 5, 10)
		orderB = Order.createActiveSell("B")("USDCAD", "4D", 5, 10)
		book = newBook.addOrder(orderA).addOrder(orderB)
	}

	test("add order") {
		val book = OrderBook.createImmutable
		val order = Order.createActiveBuy("A")("USDEUR", "5D", 5, 10)
		val newBook = book.addOrder(order)
		assert(1 == newBook.getOrders.count(o => o.id == order.id))
	}
	test("filter by party") {
		val newBook = book.getByParty("A")
		assert(1 == newBook.getOrders.count(o => o.id == orderA.id))
		assert(0 == newBook.getOrders.count(o => o.id == orderB.id))
	}

	test("filter by tenor") {
		val newBook = book.getByTenor("5D")
		assert(1 == newBook.getOrders.count(o => o.id == orderA.id))
		assert(0 == newBook.getOrders.count(o => o.id == orderB.id))
	}

	test("filter by symbol") {
		val newBook = book.getBySymbol("USDEUR")
		assert(1 == newBook.getOrders.count(o => o.id == orderA.id))
		assert(0 == newBook.getOrders.count(o => o.id == orderB.id))
	}

	test("filter by direction") {
		val newBook = book.getByDirection(Direction.BUY)
		assert(1 == newBook.getOrders.count(o => o.id == orderA.id))
		assert(0 == newBook.getOrders.count(o => o.id == orderB.id))
	}
}
