package com.scala.learn.interview.leetcode

/* imransarwar created on 07/09/2020*/
object NoOfEqualDominoes extends App {
  def numEquivDominoPairs(dominoes: Array[Array[Int]]): Int = {
    var count = 0
    for((d, i) <- dominoes.zipWithIndex) {
      for(j <- dominoes.splitAt(i+1)._2) {
        if(d(0) == j(1) && d(1) == j(0)) count += 1
      }
    }
    count
  }
}
