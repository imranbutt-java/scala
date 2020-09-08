package com.scala.learn.interview.leetcode

/* imransarwar created on 06/09/2020*/
object MinCostWorkerNoOfDays extends App {
  def minCost(days: Int, diff: Array[Int]): Int = {
    val n = diff.length
    val dp = Array.fill(n+1, days+1)(-1)

    def helper(dif: Array[Int], i: Int, j: Int): Int = {
      if(i == j) dif.slice(i,j+1).sum
//      else if(dp(i)(j) != -1) dp(i)(j)
      else {
        var minCost = Int.MaxValue
        for(k <- i until j) {
          if(dp(i)(k) == -1) dp(i)(k) = helper(dif, i, k)
          if(dp(k+1)(j) == -1) dp(k+1)(j) = helper(dif, k+1, j)

          val sum1 = dif.slice(i, k+1).reduceOption(_ max _).getOrElse(0)
          val sum2 = dif.slice(k+1, j+1).reduceOption(_ max _).getOrElse(0)
          val sum3 = dif.slice(i, j+1).reduceOption(_ max _).getOrElse(0)
//          val tmp = sum2  + sum2 + dp(i)(k) +  sum1 + dp(k+1)(j)
          val tmp = sum1 + sum2 + dp(i)(k) + dp(k+1)(j)
          minCost = Math.min(minCost, tmp)
          dp(i)(j) = minCost
        }
        minCost
      }
    }
    helper(diff, 0, days)
  }
  println(minCost(3, Array(6,3,1,4,2)))

}
