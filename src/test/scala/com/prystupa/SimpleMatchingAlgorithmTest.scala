package com.prystupa

import org.scalatest.{BeforeAndAfterAllFunctions, BeforeAndAfterEach, FunSuite}

/**
 * Date: 10/11/12
 * Time: 11:25 AM
 */
class SimpleMatchingAlgorithmTest extends FunSuite with BeforeAndAfterAllFunctions {

	var simpleMatchingAlgorithm:SimpleMatchingAlgorithm = _
	var book:OrderBook = _

	var orderA1:Order = _
	var orderA2:Order = _
	var orderA3:Order = _
	var orderA4:Order = _

	var orderB1:Order = _
	var orderB2:Order = _

	var originalSwap:MatchingUnit = _
	var matchingChain:MatchingChain = _

	beforeAll {
		simpleMatchingAlgorithm = new SimpleMatchingAlgorithm()
		val newBook = OrderBook.createImmutable
		orderA1 = Order.createActiveBuy("A")("USDEUR", "5D", 5, 7)
		orderA2 = Order.createActiveBuy("A")("USDEUR", "3D", 1, 7)
		orderA3 = Order.createActiveBuy("A")("USDEUR", "5D", 4, 7)
		orderA4 = Order.createActiveSell("A")("USDEUR", "5D", 5, 7)

		orderB1 = Order.createActiveBuy("B")("USDCAD", "4D", 3, 10)
		orderB2 = Order.createActiveSell("B")("USDEUR", "5D", 5, 10)

		book = newBook.addOrder(orderA1).addOrder(orderB1)
		originalSwap = simpleMatchingAlgorithm.createSwap(orderB1, orderB2, 7)
		matchingChain =  MatchingChain.EMPTY_CHAIN.append(originalSwap)
	}

	test("can match on instrument, price") {
		assert(simpleMatchingAlgorithm.canMatch(orderB2, orderA1, matchingChain))
	}

	test("cannot match on different instrument") {
		assert(!simpleMatchingAlgorithm.canMatch(orderB2, orderA2, matchingChain))
	}

	test("cannot match on different, price") {
		assert(!simpleMatchingAlgorithm.canMatch(orderB2, orderA3, matchingChain))
	}

	test("cannot match on same direction") {
		assert(!simpleMatchingAlgorithm.canMatch(orderB2, orderA4, matchingChain))
	}

}
