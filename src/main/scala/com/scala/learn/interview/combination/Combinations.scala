package com.scala.learn.interview.combination

/* imransarwar created on 24/11/2020*/
object Combinations extends App {
  def combine(n: Int, k: Int): List[List[Int]] = {
    (1 to n).combinations(k).map(x => x.toList).toList
  }
  combine(4, 2).foreach({
    x => x.foreach(print)
      println
  })
}
