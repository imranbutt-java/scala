package com.scala.learn.interview.array

/* imransarwar created on 12/01/2021*/
object ButterflyColor extends App {
  def countWays(n: Int, k: Int, x: Int): Int = {
    val dp = Array.ofDim[Int](n+1, k+1)
    for {
      r <- 0 to n
      c <- 0 to k
    } dp(r)(c) = -1

    val MOD = 1_000_000_007
    def countWays(ind: Int, prevColor: Int): Int = ind match {
      case index if index == n => 1
      case index if prevColor != -1 && dp(index)(prevColor) != -1 => dp(index)(prevColor)
      case index => {
        var ans = 0
        for(i <- 1 to k) {
          if (i != prevColor || prevColor == x) {
            ans += countWays(ind + 1, i)
            ans %= MOD
          }
        }
        if(prevColor != -1) dp(index)(prevColor) = ans
        ans
      }
    }

    countWays(0, -1)
  }

  println(countWays(5, 4, 3))
}