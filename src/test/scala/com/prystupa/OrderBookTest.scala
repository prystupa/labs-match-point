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
	var orderA:LinkedOrder = _
	var orderB:LinkedOrder = _

	override def beforeEach() {
		val newBook = OrderBook.createImmutable
		val orderA1 = Order.createActiveBuy("A")("USDEUR", "5D", 5.toString, 10)
		val orderA2 = Order.createActiveSell("A")("USDEUR", "6D", 5.toString, 10)
		orderA = LinkedOrder.apply(orderA1, orderA2)
		val orderB1 = Order.createActiveSell("B")("USDCAD", "4D", 5.toString, 10)
		val orderB2 = Order.createActiveBuy("B")("USDCAD", "5D", 5.toString, 10)
		orderB = LinkedOrder.apply(orderB1, orderB2)
		book = newBook.addOrder(orderA).addOrder(orderB)
	}

	test("add order") {
		val book = OrderBook.createImmutable
		val orderA1 = Order.createActiveBuy("A")("USDEUR", "5D", 5.toString, 10)
		val orderA2 = Order.createActiveSell("A")("USDEUR", "6D", 5.toString, 10)
		val order = LinkedOrder.apply(orderA1, orderA2)
		val newBook = book.addOrder(order)
		assert(1 == newBook.getOrders.count(o => o.id == order.id))
		assert(1 == newBook.getOrders.count(o => o.id == order.linkedOrder.id))
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
