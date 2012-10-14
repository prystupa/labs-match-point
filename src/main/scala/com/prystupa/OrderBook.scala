package com.prystupa

import Direction._

/**
 * Date: 10/7/12
 * Time: 9:12 PM
 */
trait OrderBook {
	def getBySymbol(symbol: String): OrderBook

	def getByParty(party: String): OrderBook

	def getByTenor(tenor: String): OrderBook

	def getByDirection(direction: Direction): OrderBook

	def getOrders: Vector[LinkedOrder]

	def addOrder(order: LinkedOrder): OrderBook

	def merge(orderBook : OrderBook) : OrderBook
}

object OrderBook {
	def createImmutable: ImmutableOrderBook = {
		new ImmutableOrderBook(Vector.empty)
	}
}
