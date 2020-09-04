package com.scala.learn.interview.leetcode

import scala.collection.mutable
import scala.util.Sorting

object ThreeSum extends App {
  def threeSum(nums: Array[Int]): List[List[Int]] = {
    var left = 0
    var right = nums.length - 1
    var ans = mutable.Set[List[Int]]()
    Sorting.quickSort(nums)

    for((n, ind) <- nums.zipWithIndex) {
      left = ind + 1
      right = nums.length - 1
      while(left < right) {
        val sum = n + nums(left) + nums(right)
        if(sum == 0) {
          ans += List(n, nums(left), nums(right))
          left += 1
          right -= 1
        }
        else if(sum > 0) right -= 1
        else left += 1
      }
    }
    ans.toList
  }

  println(threeSum(Array(-1,0,1,2,-1,-4)))
}
