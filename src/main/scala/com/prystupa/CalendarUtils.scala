package com.prystupa

/**
 * Date: 10/9/12
 * Time: 12:41 PM
 */
object CalendarUtils {
  /**
   * Checks if a particular tenor after other tenor
   * This is a simplification, as it currently only works with days
   * And proper way would be to check for settlement date (value date)
   * @param t1 - Tenor 1
   * @param t2 - Tenor 2
   * @return
   */
  def tenorAfter(t1: String, t2: String): Boolean = {
    val tenor1: Int = extractTenorNumber(t1)
    val tenor2: Int = extractTenorNumber(t2)
    tenor1 > tenor2
  }

  private def extractTenorNumber(t: String):Int = {
    augmentString(t.substring(0, t.length() - 1)).toInt
  }
}
