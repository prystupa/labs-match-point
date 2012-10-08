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
  def findWithBestPrice(direction: Direction) : Order
  def getOrders : List[Order]

}
