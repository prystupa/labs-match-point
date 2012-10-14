package com.prystupa

import collection.mutable.ListBuffer


/**
 * Represents a chain of matched orders, along with corresponding notional
 *
 * Date: 10/7/12
 * Time: 5:07 PM
 */
class MatchingChain(val matchingUnits: List[MatchingUnit], val matchNotional: Long) {


  /**
   * Represents matching result chain
   * @param matchingUnit appends matching unit
   * @return
   */
  def append(matchingUnit: MatchingUnit): MatchingChain = {
    val newNotional = matchingUnit.notional
    val newMatchingUnits = matchingUnit :: matchingUnits
    new MatchingChain(newMatchingUnits, newNotional)
  }
}

object MatchingChain {
    val EMPTY_CHAIN = new MatchingChain(List(), 0)
}
