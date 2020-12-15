package com.scala.learn.interview.array

/* imransarwar created on 29/11/2020*/
object FirstMissingPositive extends App {
  def firstMissingPositive(nums: Array[Int]): Int = {
    val n = nums.length

    if(nums.filter(_ == 1).size == 0) return 1

    // Change all less than 0 and greater than n to 1
    for(i <- 0 until n)
      if(nums(i) <= 0 || nums(i) > n) nums(i) = 1

    for(i <- 0 until n) {
      if(nums(i) > 0 && nums(nums(i) - 1) > 0)
        nums(nums(i) - 1) = -1 * nums(nums(i) - 1)
    }

    for(i <- 0 until n) {
      if(nums(i) > 0) return i + 1
    }
    n + 1
  }

  println(firstMissingPositive(Array(3,4,-1,1)))
}
