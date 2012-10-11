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
class MatchingServiceSteps {

	@When("^market taker \"([^\"]*)\" submits the following order:$")
	def market_taker_submits_the_following_order(taker: String, table: DataTable) {

		// add order to matching unit
		throw new PendingException()
	}

	@Then("^no trades are generated$")
	def no_trades_are_generated() {

		// get proposed trades from the matching unit and assert the list is empty
		throw new PendingException()
	}


	@Then("^the following trade suggestions are generated:$")
	def the_following_trade_suggestions_are_generated(table: DataTable) {

		// get suggested trades from matching unit and assert they equal List(expected)
		throw new PendingException()
	}

}
