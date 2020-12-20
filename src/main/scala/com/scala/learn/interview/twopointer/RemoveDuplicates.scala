package com.scala.learn.interview.twopointer

/* imransarwar created on 19/12/2020*/
/**
 * Given an array of sorted numbers, remove all duplicates from it. You should not use any extra space;
 * after removing the duplicates in-place return the length of the subarray that has no duplicate in it.
 *
 * Example 1:
 *
 * Input: [2, 3, 3, 3, 6, 9, 9]
 * Output: 4
 * Explanation: The first four elements after removing the duplicates will be [2, 3, 6, 9].
 */
object RemoveDuplicates extends App {
  def removeDuplicate(nums: Array[Int]): Int = {
    var lastNonDuplicate = 1
    for((num, _) <- nums.zipWithIndex) {
      if(nums(lastNonDuplicate - 1) != num) {
        nums(lastNonDuplicate) = num
        lastNonDuplicate += 1
      }
    }
    lastNonDuplicate
  }

  assert(removeDuplicate(Array(2, 3, 3, 3, 6, 9, 9)) == 4)
  assert(removeDuplicate(Array(2, 2, 2, 11)) == 2)
}
