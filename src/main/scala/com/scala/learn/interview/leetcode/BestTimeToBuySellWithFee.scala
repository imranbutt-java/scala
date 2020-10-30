package com.scala.learn.interview.leetcode

/* imransarwar created on 10/09/2020*/
object BestTimeToBuySellWithFee extends App {
  def maxProfit(prices: Array[Int], fee: Int): Int = {
    if (prices.length <= 1) return 0
    var days = prices.length
    val buy = Array.ofDim[Int](days)
    val sell = Array.ofDim[Int](days)
    buy(0) = -prices(0) - fee

    for (i <- 1 until days) {
      buy(i) = Math.max(buy(i-1), sell(i-1) - prices(i) - fee) // keep the same as day i-1, or buy from sell status at day i-1
      sell(i) = Math.max(sell(i-1), buy(i-1) + prices(i)) // keep the same as day i-1, or sell from buy status at day i-1
    }
    sell(days - 1)
  }

  maxProfit(Array(1,3,2,8,4,9), 2)
}
