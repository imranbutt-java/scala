package com.scala.learn.interview.twopointer

import scala.collection.mutable

/* imransarwar created on 19/12/2020*/
/**
 * Given an array of unsorted numbers, find all unique triplets in it that add up to zero.
 *
 * Example 1:
 *
 * Input: [-3, 0, 1, 2, -1, 1, -2]
 * Output: [-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]
 * Explanation: There are four unique triplets whose sum is equal to zero.
 */
object TripletSumToZero extends App {
  def tripletSumToZero(nums: Array[Int]): Array[Array[Int]] = {
    val sorted = nums.sorted
    var left = 1
    var right = nums.length - 1
    val ans = mutable.ListBuffer[Array[Int]]()

    for((num, i) <- sorted.zipWithIndex) {
      left = i + 1
      right = nums.length - 1
      while (left <= right) {
        val sum = num + sorted(left) + sorted(right)
        if (sum == 0) {
          ans.addOne(Array(num, sorted(left), sorted(right)))
          left += 1
          right -= 1
        }
        else if (sum > 0) right -= 1
        else left += 1
      }
    }
    ans.toArray
  }

  for {
    n <- tripletSumToZero(Array(-3, 0, 1, 2, -1, 1, -2))
  } {
    println(n.mkString(","))
  }
}
