package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 27/08/2020*/
object TwoSum extends App {
  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    var map = mutable.Map[Int, Int]()
    for((num, ind) <- nums.zipWithIndex) {
      if(map.contains(num)) return Array(map(num), ind)
      else map += (target - num) -> ind
    }
    Array[Int]()
  }
  println(twoSum(Array(2, 7, 11, 13), 9))
}
