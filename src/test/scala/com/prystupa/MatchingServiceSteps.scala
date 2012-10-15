package com.prystupa

import cucumber.annotation.en.{Then, When, Given}
import cucumber.table.DataTable
import scala.collection.JavaConversions._
import scala.collection._
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
	val symbol = "USDEUR"

	@When("^market taker \"([^\"]*)\" submits the following order:$")
	def market_taker_submits_the_following_order(taker: String, table: DataTable) {
		orderBook = orderBook.merge(createOrderBook(table, OrderType.ACTIVE, taker))
	}

	@Then("^no trades are generated$")
	def no_trades_are_generated() {
		val matchingEngine = new MatchingEngine(orderBook)
		val matches:mutable.Map[Int, MatchingResult] = matchingEngine.createMatch()
		val nonZeroMatch = matches.find(keyVal => !keyVal._2.isEmpty )
		assert(None == nonZeroMatch)
	}

	@Then("^the following trade suggestions are generated:$")
	def the_following_trade_suggestions_are_generated(table: DataTable) {
		val expectedMatchingChain = createExpectedMatches(table)
		val matchingEngine = new MatchingEngine(orderBook)
		val matches:mutable.Map[Int, MatchingResult] = matchingEngine.createMatch()
		//2 matches for party  C
		assert(matches.size == 2)
		//Find no zero match
		val nonZeroMatch = matches.find(keyVal => !keyVal._2.isEmpty )
		assert(nonZeroMatch != None)
		//Assrt it has only 1 chain
		assert(nonZeroMatch.get._2.matchingChains.size() == 1)
		//Get matching chain
		val matchingChain = nonZeroMatch.get._2.matchingChains.get(0)
		//Should have 4 matches
		assert(matchingChain.matchingUnits.size == 3)
		//Assert that same economics
		assert(expectedMatchingChain.isSameEconomics(matchingChain))
	}

	@Given("^market maker \"([^\"]*)\" quoted the following markets:$")
	def market_maker_quoted_the_following_markets(maker: String, table: DataTable) {
		orderBook = orderBook.merge(createOrderBook(table, OrderType.STANDING, maker))
	}

	def createExpectedMatches(table: DataTable):MatchingChain = {
		var ret = MatchingChain.EMPTY_CHAIN
		for(row <- table.asMaps().iterator) {
			val tenor = row.get("Day")
			val price = row.get("Rate")
			val buyer = row.get("Buyer")
			val seller = row.get("Seller")
			val notional = augmentString(row.get("Size")).toLong
			val buyOrder = Order.create(null)(Direction.BUY)(buyer)(symbol, tenor, price, notional)
			val sellOrder = Order.create(null)(Direction.SELL)(seller)(symbol, tenor, price, notional)
			ret = ret.append(new MatchingUnit(notional, buyOrder, sellOrder))
		}
		ret
	}

	def createOrderBook(table: DataTable, orderType: OrderType, party:String):OrderBook = {
		def isBuy(row: java.util.Map[String, String]):Boolean = row.get("Bid").length() > 0
		var order1:Order = null
		var order2:Order = null
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
			val price = row.get(priceKey)
			val notional = augmentString(row.get(notionalKey)).toLong
			val tenor = row.get("Day")
			val order = Order.create(orderType)(direction)(party)(symbol, tenor, price.toString, notional)
			if (order1 == null) order1 = order
			else order2 = order
		}
		val linkedOrder = LinkedOrder.link(order1, order2)
		OrderBook.createImmutable.addOrder(linkedOrder)
	}

}
