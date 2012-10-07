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

  @Given("^market maker \"([^\"]*)\" quoted the following markets:$")
  def market_maker_quoted_the_following_markets(maker: String, table: DataTable) {
    throw new PendingException()
  }

  @Then("^RFQ service quotes the following price for \"([^\"]*)\":$")
  def RFQ_service_quotes_the_following_price_for_(instrument: String, table: DataTable) {
    // Express the Regexp above with the code you wish you had
    // For automatic conversion, change DataTable to List<YourType>
    throw new PendingException()
  }

  @Then("^RFQ service cannot quote a price for \"([^\"]*)\"$")
  def RFQ_service_cannot_quote_a_price_for(instrument: String) {
    // Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  @Then("^no trades are generated$")
  def no_trades_are_generated() {
    // Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  @When("^market taker \"([^\"]*)\" submits the following order:$")
  def market_taker_submits_the_following_order(taker: String, table: DataTable) {
    // Express the Regexp above with the code you wish you had
    // For automatic conversion, change DataTable to List<YourType>
    throw new PendingException()
  }

}
