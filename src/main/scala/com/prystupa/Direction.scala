package com.prystupa

/**
 * Date: 10/7/12
 * Time: 4:39 PM
 *
 * Represnets direction of an order, either buy or sell
 */
object Direction extends Enumeration {
  type Direction = Value
  val BUY, SELL = Value
  def reverse(direction: Direction): Direction = {
    if (direction == BUY) SELL else BUY
  }
}
