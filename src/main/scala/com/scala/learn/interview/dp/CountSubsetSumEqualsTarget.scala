package com.scala.learn.interview.dp

/* imransarwar created on 16/12/2020*/
object CountSubsetSumEqualsTarget extends App {
  def countSubsetSumEqualsTarget(nums: Array[Int], target: Int): Int = {
    val R = nums.length + 1
    val C = target + 1
    val dp = Array.ofDim[Int](R, C)
    for(c <- 0 until C) dp(0)(c) = 0
    for(r <- 0 until R) dp(r)(0) = 1

    for(r <- 1 until R)
      for(c <- 1 until C) {
        if(nums(r-1) <= c) dp(r)(c) = dp(r-1)(c) + dp(r-1)(c-nums(r-1))
        else dp(r)(c) = dp(r-1)(c)
      }

    dp(R-1)(C-1)
  }

  println(countSubsetSumEqualsTarget(Array(2,3,5,6,8,10), 10))
}
