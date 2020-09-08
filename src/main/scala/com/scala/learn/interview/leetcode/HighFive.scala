package com.scala.learn.interview.leetcode

/* imransarwar created on 07/09/2020*/
object HighFive extends App {
  def highFive(items: Array[Array[Int]]): Array[Array[Int]] = {
    val map = items.groupMap(x => x(0))(x => x(1))
    def topFiveAvg(score: Array[Int]): Int = {
      val topList = score.sorted.take(5)
      (topList.foldLeft(0.0)(_+_) / topList.length).toInt
    }
    map.map(x => Array(x._1, topFiveAvg(x._2))).toArray.sortBy(x => x(0))
  }
}
