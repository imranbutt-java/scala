package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 31/08/2020*/
object SubarraySumToK extends App {
  def subarraySum(nums: Array[Int], k: Int): Int = {
    val map = mutable.Map[Int, Int]().withDefault(x => 0)
    var sum = 0
    var count = 0

    // Adding key 0, so that when we minus the k from sum, we want to find either adding the current num we are getting sum
    map.put(0, 1)
    for(i <- 0 until nums.length) {
      sum += nums(i)
      if(map.contains(sum - k))
        count = count + map(sum - k)
      map.put(sum, 1 + map(sum))
    }
    count

    // var ans = 0
    // for(i <- 0 until nums.length) {
    //     var sum = 0
    //     for(j <- i until nums.length) {
    //         sum += nums(j)
    //         if(sum == k) ans += 1
    //     }
    // }
    // ans
  }

  subarraySum(Array(3,4,3,2), 4)
}
