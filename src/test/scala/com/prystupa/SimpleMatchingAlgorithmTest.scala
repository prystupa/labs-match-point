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
	var orderA5:Order = _

	var orderB1:Order = _
	var orderB2:Order = _
	var orderB3:Order = _

	var originalSwap:MatchingUnit = _
	var matchingChain:MatchingChain = _

	beforeAll {
		simpleMatchingAlgorithm = new SimpleMatchingAlgorithm()
		orderA1 = Order.createActiveBuy("A")("USDEUR", "5D", 5.toString, 7)
		orderA2 = Order.createActiveBuy("A")("USDEUR", "3D", 1.toString, 7)
		orderA3 = Order.createActiveBuy("A")("USDEUR", "5D", 4.toString, 7)
		orderA4 = Order.createActiveSell("A")("USDEUR", "5D", 5.toString, 10)
		orderA5 = Order.createActiveSell("A")("USDCAD", "5D", 6.toString, 10)

		orderB1 = Order.createActiveBuy("B")("USDCAD", "4D", 3.toString, 10)
		orderB2 = Order.createActiveSell("B")("USDEUR", "5D", 5.toString, 10)
		orderB3 = Order.createActiveBuy("B")("USDEUR", "5D", 5.toString, 7)

		matchingChain =  MatchingChain.EMPTY_CHAIN
	}

	test("can match on instrument, price") {
		assert(simpleMatchingAlgorithm.canMatch(orderB2, orderA1, matchingChain))
	}

	test("cannot match on different instrument") {
		assert(!simpleMatchingAlgorithm.canMatch(orderB2, orderA2, matchingChain))
	}

	test("cannot match on different price") {
		assert(!simpleMatchingAlgorithm.canMatch(orderB2, orderA3, matchingChain))
	}

	test("cannot match on same direction") {
		assert(!simpleMatchingAlgorithm.canMatch(orderB2, orderA4, matchingChain))
	}

	test("cannot match same counterparty") {
		assert(!simpleMatchingAlgorithm.canMatch(orderB2, orderB3, matchingChain))
	}

	test("create match uses lowest notional") {
		assert(simpleMatchingAlgorithm.createMatch(orderB2, orderA1, None).notional == 7)
		assert(simpleMatchingAlgorithm.createMatch(orderB2, orderA1, Some(3)).notional == 3)
	}




}
