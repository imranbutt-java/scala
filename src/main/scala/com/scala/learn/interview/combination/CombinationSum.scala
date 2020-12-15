package com.scala.learn.interview.combination

import scala.collection.mutable

/* imransarwar created on 24/11/2020*/
object CombinationSum extends App {
  def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
    val result = mutable.ListBuffer[List[Int]]()

    def dfs(remaining: Int, start: Int, tmp: List[Int]): Unit = {
      if (remaining == 0) result += tmp
      else if (remaining > 0)
        for (i <- start until candidates.length)
          dfs(remaining - candidates(i), i, tmp :+ candidates(i))
    }

    dfs(target, 0, List())
    result.toList
  }

  combinationSum(Array(2,3,6,7), 7).foreach(println)
}
