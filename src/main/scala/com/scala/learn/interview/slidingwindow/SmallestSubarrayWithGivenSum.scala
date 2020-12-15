package com.scala.learn.interview.slidingwindow

/* imransarwar created on 15/12/2020*/
/**
 * Given an array of positive numbers and a positive number ‘S,’ find the length of the smallest contiguous
 * subarray whose sum is greater than or equal to ‘S’. Return 0 if no such subarray exists.
 *
 * Example 1:
 *
 * Input: [2, 1, 5, 2, 3, 2], S=7
 * Output: 2
 * Explanation: The smallest subarray with a sum great than or equal to '7' is [5, 2].
 */
object SmallestSubarrayWithGivenSum extends App {
  def smallestSubarrayWithGivenSum(nums: Array[Int], S: Int): Int = {
    var smallestCount = Int.MaxValue
    var left = 0
    var windowSum = 0

    for((num, right) <- nums.zipWithIndex) {
      windowSum += num

      while(windowSum >= S) {
        smallestCount = Math.min(smallestCount, right - left + 1)
        windowSum -= nums(left)
        left += 1
      }
    }
    smallestCount
  }

  println(smallestSubarrayWithGivenSum(Array(2, 1, 5, 2, 3, 2), 7))
}
