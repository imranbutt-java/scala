package com.scala.learn.interview.leetcode

/* imransarwar created on 06/09/2020*/
object MinCostToCutStick extends App {
  def minCost(n: Int, cuts: Array[Int]): Int = {
    val rod = Array.tabulate(n)(n => n)
    val cutLen = cuts.length
    val dp = Array.ofDim[Int](n+1, cutLen + 1)

    for(i <- 1 to n)
      for(j <- 1 to cutLen) {
        if(i < j)
          dp(i)(j) = dp(i-1)(j)
        else
          dp(i)(j) = Math.min(dp(i-1)(j), cuts(i-1) + dp(i)(j - rod(i-1)))

      }
    dp(n)(cutLen)
  }
  minCost(7, Array(1,3,4,5))
}
