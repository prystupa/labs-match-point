package com.prystupa

import cucumber.annotation.en.{Then, When, Given}
import cucumber.table.DataTable
import cucumber.runtime.PendingException
import scala.collection.JavaConversions._
import OrderType._
import Direction._

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 10/4/12
 * Time: 4:07 PM
 */
class MatchingServiceSteps {

	var orderBook:OrderBook = OrderBook.createImmutable

	@When("^market taker \"([^\"]*)\" submits the following order:$")
	def market_taker_submits_the_following_order(taker: String, table: DataTable) {
		orderBook = orderBook.merge(createOrderBook(table, OrderType.ACTIVE, taker))
	}

	@Then("^no trades are generated$")
	def no_trades_are_generated() {
		System.out.println("No trades generated");
	}


	@Then("^the following trade suggestions are generated:$")
	def the_following_trade_suggestions_are_generated(table: DataTable) {
		System.out.println("The following trade suggestions are generated");
	}

	@Given("^market maker \"([^\"]*)\" quoted the following markets:$")
	def market_maker_quoted_the_following_markets(maker: String, table: DataTable) {
		orderBook = orderBook.merge(createOrderBook(table, OrderType.STANDING, maker))
	}

	def createOrderBook(table: DataTable, orderType: OrderType, party:String):OrderBook = {
		var orderBook:OrderBook = OrderBook.createImmutable
		def isBuy(row: java.util.Map[String, String]):Boolean = row.get("Bid").length() > 0
		for(row <- table.asMaps().iterator) {
			var priceKey = ""
			var notionalKey = ""
			var direction:Direction = null
			if (isBuy(row)) {
				priceKey = "Bid"
				notionalKey = "Bid AMT"
				direction = Direction.BUY
			} else {
				priceKey = "Offer"
				notionalKey = "Offer AMT"
				direction = Direction.SELL
			}
			val symbol = "USDEUR"
			val price = row.get(priceKey)
			val notional = augmentString(row.get(notionalKey)).toLong
			val tenor = row.get("Day")
			val order = Order.create(orderType)(direction)(party)(symbol, tenor, price.toString, notional)
			orderBook = orderBook.addOrder(order)
		}
		orderBook
	}

}
