package com.prystupa

import collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Date: 10/9/12
 * Time: 9:01 PM
 *
 * Simple implementation of order book,
 * can be improved for performance/thread safety
 */
class ImmutableOrderBook (val orders:Vector[LinkedOrder]) extends OrderBook {

	def getBySymbol(symbol: String): OrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.instrument.symbol.equals(symbol)))
	}

	def getByParty(party: String): OrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.party.equals(party)))
	}

	def getByTenor(tenor: String): OrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.instrument.tenor.equals(tenor)))
	}

	def getByDirection(direction: Direction.Direction): OrderBook = {
		new ImmutableOrderBook(orders.filter(order => order.direction == direction))
	}

	def merge(orderBook: OrderBook) : OrderBook = {
		new ImmutableOrderBook(orders ++ orderBook.getOrders)
	}


	def getOrders = { orders }

	def addOrder(order: LinkedOrder): OrderBook = {
		new ImmutableOrderBook(orders :+ order :+ order.linkedOrder)
	}

}
