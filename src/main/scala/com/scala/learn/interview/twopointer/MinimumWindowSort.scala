package com.scala.learn.interview.twopointer

/* imransarwar created on 20/12/2020*/
/**
 * Minimum Window Sort (medium) #
 * Given an array, find the length of the smallest subarray in it which when sorted will sort the whole array.
 *
 * Example 1:
 *
 * Input: [1, 2, 5, 3, 7, 10, 9, 12]
 * Output: 5
 * Explanation: We need to sort only the subarray [5, 3, 7, 10, 9] to make the whole array sorted
 */
object MinimumWindowSort extends App {
  def minimumWindowSort(nums: Array[Int]): Int = {
    /*
    // Only to satisfy the last condition if all array sorted
    var left = -1
    var right = -2

    //iterate from beginning of array
    //find the last element which is smaller than the last seen max from
    //its left side and mark it as right
    var max = Int.MinValue
    for((num, i) <- nums.zipWithIndex) {
      max = Math.max(max, num)
      if(num < max) right = i
    }

    //iterate from end of array
    //find the last element which is bigger than the last seen min from
    //its right side and mark it as begin
    var min = Int.MaxValue
    for((num, i) <- nums.zipWithIndex.reverse) {
      min = Math.min(min, num)
      if(num > min) left = i
    }

    right - left + 1
    */
    var left = 0
    var right = nums.length - 1
    val len = nums.length

    // Move Left to Right to find first unordered point
    while(left < len - 1 && nums(left) <= nums(left + 1)) left += 1
    // If all array sorted
    if(left == len - 1) return 0

    while(right > 0 && nums(right) >= nums(right - 1)) right -= 1

    val min = nums.slice(left, right+1).scanLeft(Int.MaxValue)(_ min _).min
    val max = nums.slice(left, right+1).scanLeft(Int.MinValue)(_ max _).max

    // Expanding Subarray
    while(left > 0 && nums(left - 1) > min) left -= 1
    while(right < len - 1 && nums(right + 1) < max) right += 1

    right - left + 1
  }

  println(minimumWindowSort(Array(1, 2, 5, 3, 7, 10, 9, 12)))
  println(minimumWindowSort(Array(1, 3, 2, 0, -1, 7, 10)))
  println(minimumWindowSort(Array(2,3,3,2,4)))

}
