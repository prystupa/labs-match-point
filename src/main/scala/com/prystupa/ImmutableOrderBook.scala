package com.prystupa

import collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Date: 10/9/12
 * Time: 9:01 PM
 *
 * Simple implementation of order book,
 * can be improved for performance/thread safety
 */
class ImmutableOrderBook (val orders:Vector[Order]) extends OrderBook {

	def getBySymbol(symbol: String): ImmutableOrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.instrument.symbol.equals(symbol)))
	}

	def getByParty(party: String): ImmutableOrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.party.equals(party)))
	}

	def getByTenor(tenor: String): ImmutableOrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.instrument.tenor.equals(tenor)))
	}

	def getByDirection(direction: Direction.Direction): ImmutableOrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.direction == direction))
	}

	def getOrders = { orders }

	def addOrder(order: Order) { orders :+ order }

}
