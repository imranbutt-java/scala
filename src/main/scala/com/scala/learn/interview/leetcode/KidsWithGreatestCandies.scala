package com.scala.learn.interview.leetcode

/* imransarwar created on 23/08/2020*/
class KidsWithGreatestCandies {
  def kidsWithCandies(candies: Array[Int], extraCandies: Int): Array[Boolean] = {
    val max = candies.max
    candies.map {
      candy => candy + extraCandies >= max
    }
  }
}
