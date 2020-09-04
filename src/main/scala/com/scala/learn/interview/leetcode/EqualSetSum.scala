package com.scala.learn.interview.leetcode

/* imransarwar created on 04/07/2020*/
object EqualSetSum extends App {
  /*
   * Set = {1, 5, 11, 5 }
   */
  def canPartition(nums: Array[Int]): Boolean = {
    // Backtracking with DFS
    val len = nums.length - 1
    def canPartition(index: Int, remainingSum: Int): Boolean = {
      if(remainingSum == 0) true
      else if(index > len || remainingSum < 0) false
      else if(canPartition(index + 1, remainingSum - nums(index)) ) true
      else {
        val currentNum = nums(index)
        val j = nums.indexWhere(x => x != currentNum, index)
        //{1,5,5,5}
        val newIndex = if(j == -1) index+1 else j
        canPartition(newIndex, remainingSum)
      }
    }
    val total = nums.foldLeft(0)(_+_)
    if(total % 2 == 1) false
    else canPartition(0, total/2)
  }

  println(canPartition(Array(1,5,11,5)))
  // 98 = 1
  // 1 = 100
  println(canPartition(Array(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,100)))

}
