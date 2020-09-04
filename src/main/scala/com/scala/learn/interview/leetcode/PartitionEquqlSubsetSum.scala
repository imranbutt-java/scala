package com.scala.learn.interview.leetcode

/* imransarwar created on 01/09/2020*/
object PartitionEquqlSubsetSum extends App {
  def canPartition(nums: Array[Int]): Boolean = {
    val len = nums.length

    def toBeOrNotToBe(index: Int, target: Int): Boolean = {
      if (target == 0) true
      else if (index >= len || target < 0) false
      else if(toBeOrNotToBe(index + 1, target-nums(index))) true
      else {
        val j = nums.indexWhere(x => nums(index) != x, index)
        val newIndex = if(j == -1) index+1 else j
        toBeOrNotToBe(newIndex, target)
      }
    }

    val total = nums.foldLeft(0)(_ + _)
    if (total % 2 == 1) false
    else toBeOrNotToBe(0, total / 2)
  }
}
