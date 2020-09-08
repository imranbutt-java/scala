package com.scala.learn.interview.leetcode

/* imransarwar created on 07/09/2020*/
object TwoSubarraysSumEqual extends App {
  def solution(a: Array[Int], b: Array[Int]): Int = {
    // write your code in Scala 2.12
    var fairIndiciesCount = 0
    for(i <- 1 to a.length) {
      val (aSumFirst, aSumSecond) = (a.splitAt(i)._1, a.splitAt(i)._2)
      val (bSumFirst, bSumSecond) = (b.splitAt(i)._1, b.splitAt(i)._2)

      val aSum = aSumFirst.sum
      val isAnySubArrayEmpty = aSumFirst.isEmpty || aSumSecond.isEmpty || bSumFirst.isEmpty || bSumSecond.isEmpty
      if(!isAnySubArrayEmpty &&
        aSum == aSumSecond.sum && aSum == bSumFirst.sum && aSum == bSumSecond.sum) fairIndiciesCount += 1
    }
    fairIndiciesCount
  }
//  println(solution(Array(1,4,2,-2,5), Array(7,-2,-2,2,5)))
//  println(solution(Array(3,2,6), Array(4,1,6)))
//  println(solution(Array(4,-1,0,3), Array(-2,5,0,3)))
  println(solution(Array(2,-2,-3,3), Array(0,0,-4,4)))
}
