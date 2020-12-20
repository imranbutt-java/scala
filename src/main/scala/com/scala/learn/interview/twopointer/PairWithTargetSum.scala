package com.scala.learn.interview.twopointer

/* imransarwar created on 19/12/2020*/
/**
 * Given an array of sorted numbers and a target sum, find a pair in the array whose sum is equal to the
 * given target.
 *
 * Write a function to return the indices of the two numbers (i.e. the pair) such that they add up to
 * the given target.
 *
 * Example 1:
 *
 * Input: [1, 2, 3, 4, 6], target=6
 * Output: [1, 3]
 * Explanation: The numbers at index 1 and 3 add up to 6: 2+4=6
 */
object PairWithTargetSum extends App {
  def pairWithTargetSum(nums: Array[Int], target: Int): Array[Int] = {
    var left = 0
    var right = nums.length - 1

    while(left < right) {
      val sum = nums(left) + nums(right)
      if(sum == target) return Array(left, right)
      else if(sum < target) left += 1
      else right -=1
    }
    Array(-1,-1)
  }
  pairWithTargetSum(Array(1, 2, 3, 4, 6), 6).foreach(println)
}
