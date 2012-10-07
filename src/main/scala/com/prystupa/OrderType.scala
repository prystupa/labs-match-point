package com.prystupa

/**
 * Date: 10/7/12
 * Time: 4:45 PM
 *
 * Represents order type either standing or active
 * Standing orders submitted by MM, Active by MT
 */
object OrderType extends Enumeration {
  type OrderType = Value
  val STANDING, ACTIVE = Value
}
