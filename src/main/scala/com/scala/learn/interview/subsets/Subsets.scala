package com.scala.learn.interview.subsets

import scala.collection.mutable._

/* imransarwar created on 26/12/2020*/
/**
 * Given an integer array nums, return all possible subsets (the power set).
 * The solution set must not contain duplicate subsets.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3]
 * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 */
object Subsets extends App {
  def subsets(nums: Array[Int]): List[List[Int]] = {
    val size = nums.length
    val ans = ListBuffer[List[Int]]()

    def backtrack(tmp: ListBuffer[Int], start: Int): Unit = {
      ans.addOne(tmp.toList)
      for(i <- start until size) {
        tmp.addOne(nums(i))
        backtrack(tmp, i+1)
        tmp.remove(tmp.size - 1)
      }
    }

    backtrack(ListBuffer[Int](), 0)
    ans.toList
  }
}
