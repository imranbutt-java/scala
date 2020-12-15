package com.scala.learn.interview.slidingwindow

/* imransarwar created on 15/12/2020*/
/**
 * Given an array of positive numbers and a positive number ‘k,’ find the maximum sum of any contiguous subarray of size ‘k’.
 *
 * Example 1:
 *
 * Input: [2, 1, 5, 1, 3, 2], k=3
 * Output: 9
 * Explanation: Subarray with maximum sum is [5, 1, 3].
 */
object MaxSumSubarraySizeK extends App {
  def maxSumSubarraySizeK(nums: Array[Int], k: Int): Int = {
    var left = 0
    var max = 0
    var sum = 0
    for((num, right) <- nums.zipWithIndex) {
      sum += num
      if(right >= k-1) {
        max = Math.max(max, sum)
        sum -= nums(left)
        left += 1
      }
    }
    max
  }
  println(maxSumSubarraySizeK(Array(2,1,5,1,3,2), 3))
}
