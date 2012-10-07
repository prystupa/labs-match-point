package com.prystupa

import Direction._
import OrderType._

/**
 * Date: 10/7/12
 * Time: 4:32 PM
 *
 * Represents order object with the following attributes
 * id - order identifier
 * price - price at which order was submited
 *
 * direction - either buy or sell
 * instrument - code for a trading instrument. This is a simplification,
 * usually this will be an id of a generated instrument
 * order type - either standing or active
 *
 * This class should be immutable
 */
class Order(id:Int, price: BigDecimal, notional: Int, direction: Direction, instrument: String, orderType: OrderType) {


}
