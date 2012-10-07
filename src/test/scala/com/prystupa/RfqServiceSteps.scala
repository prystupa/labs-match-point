package com.prystupa

import cucumber.annotation.en.{Then, When, Given}
import cucumber.table.DataTable
import cucumber.runtime.PendingException

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 10/4/12
 * Time: 4:07 PM
 */
class RfqServiceSteps {

  val matchingUnit = new MatchingUnit

  @Given("^market maker \"([^\"]*)\" quoted the following markets:$")
  def market_maker_quoted_the_following_markets(maker: String, table: DataTable) {

    // add quotes to matching unit
    throw new PendingException()
  }

  @Then("^RFQ service quotes the following price for \"([^\"]*)\":$")
  def RFQ_service_quotes_the_following_price_for_(instrument: String, table: DataTable) {

    // get RFQ from matching unit given the instrument and assert that quote is Some(expected)
    throw new PendingException()
  }

  @Then("^RFQ service cannot quote a price for \"([^\"]*)\"$")
  def RFQ_service_cannot_quote_a_price_for(instrument: String) {

    // get a quote from matching unit and assert that quote is None
    throw new PendingException()
  }

  @Then("^no trades are generated$")
  def no_trades_are_generated() {

    // get proposed trades from the matching unit and assert the list is empty
    throw new PendingException()
  }

  @When("^market taker \"([^\"]*)\" submits the following order:$")
  def market_taker_submits_the_following_order(taker: String, table: DataTable) {

    // add order to matching unit
    throw new PendingException()
  }

  @Then("^the following trade suggestions are generated:$")
  def the_following_trade_suggestions_are_generated(table: DataTable) {

    // get suggested trades from matching unit and assert they equal List(expected)
    throw new PendingException()
  }


}
