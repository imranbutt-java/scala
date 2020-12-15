package com.scala.learn.interview.slidingwindow

/* imransarwar created on 15/12/2020*/
/**
 * Given an array containing 0s and 1s, if you are allowed to replace no more than ‘k’ 0s with 1s,
 * find the length of the longest contiguous subarray having all 1s.
 *
 * Example 1:
 *
 * Input: Array=[0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1], k=2
 * Output: 6
 * Explanation: Replace the '0' at index 5 and 8 to have the longest contiguous subarray of 1s having length 6.
 */
object LongestSubarrayWithZeroAfterRep extends App {
  def longSubarrayAfterZeroToOne(nums: Array[Int], k: Int): Int = {
    var left = 0
    var maxCount = 0
    var windowOneCount = 0
    val count = Array.fill(2)(0)

    for((num, right) <- nums.zipWithIndex) {
      if(num == 1)
        windowOneCount += 1

      if(right - left + 1 - windowOneCount > k) {
        if(nums(left) == 1)
          windowOneCount -= 1
        left += 1
      }
      maxCount = Math.max(maxCount, right - left + 1)
    }
    maxCount
  }

  println(longSubarrayAfterZeroToOne(Array(0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1), 2))
}
