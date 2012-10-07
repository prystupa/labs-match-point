package com.prystupa

/**
 * Date: 10/7/12
 * Time: 4:53 PM
 * Encapsulates matching algorithm
 */
trait MatchingAlgorithm {
  /**
   * Encapsulates matching cost between two orders,
   * Order chains can be ranked based on cost
   * @param order1 - order to determine cost
   * @param order2 - order to determine cost
   * @return
   */
  def matchingCost(order1: Order, order2: Order) : BigDecimal


  /**
   * Test if two orders can be matched based on matching algorithm
   * @param order1 - order to check for match
   * @param order2 - order to check for match
   * @return
   */
  def canMatch(order1: Order, order2: Order) : Boolean
}
