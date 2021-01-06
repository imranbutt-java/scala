package com.scala.learn.interview.twopointer

import com.scala.learn.interview.twopointer.SubarrayProductLessThanTarget.productLessThan

import scala.collection.mutable

/* imransarwar created on 20/12/2020*/
/**
 * Given an array with positive numbers and a target number, find all of its contiguous
 * subarrays whose product is less than the target number.
 *
 * Example 1:
 *
 * Input: [2, 5, 3, 10], target=30
 * Output: [2], [5], [2, 5], [3], [5, 3], [10]
 * Explanation: There are six contiguous subarrays whose product is less than the target.
 */
object SubarrayProductLessThanTarget extends App {
  def productLessThan(nums: Array[Int], target: Int): Array[Array[Int]] = {
    if(target == 0) return Array.empty

    var left = 0
    var product = 1
    var lastZero = -1
    val ans = mutable.ListBuffer[Array[Int]]()
    for((num, right) <- nums.zipWithIndex) {
      product *= num
      if(num == 0) {
        lastZero = 0
        product = 1
        left = right + 1
      } else {
        while (product >= target && left <= right) {
          product /= nums(left)
          left += 1
        }
      }
      val tmp = mutable.ListBuffer[Int]()
      for (i <- right to left by -1) {
        tmp.addOne(nums(i))
        ans.addOne(tmp.toArray)
      }
    }
    ans.toArray
  }

  for {
    n <- productLessThan(Array(2, 5, 0, 10), 30)
  } println(n.mkString(","))
}
