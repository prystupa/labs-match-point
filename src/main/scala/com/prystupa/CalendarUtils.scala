package com.prystupa

/**
 * Date: 10/9/12
 * Time: 12:41 PM
 */
object CalendarUtils {
  /**
   * Checks if a particular instrument has tenor after other instrument
   * This is a simplification, as it currently only works with days
   * And proper way would be to check for settlement date (value date)
   * @param instrument1
   * @param instrument2
   * @return
   */
  def tenorAfter(instrument1: Instrument, instrument2: Instrument): Boolean = {
    val tenor1: Int = extractTenorNumber(instrument1)
    val tenor2: Int = extractTenorNumber(instrument1)
    tenor2 > tenor1
  }

  private def extractTenorNumber(instrument: Instrument):Int = {
    augmentString(instrument.tenor.substring(0, instrument.tenor.length() - 1)).toInt
  }
}
