package com.scala.learn.interview.intervals

/* imransarwar created on 23/12/2020*/
object InsertInterval extends App {
  def insert(intervals: Array[Array[Int]], newInterval: Array[Int]): Array[Array[Int]] = {
    intervals.foldLeft(List[Array[Int]]()) {
      case (first :: tail, next) if first.last < newInterval.head =>
        first :: next :: tail
      case (first :: tail, next) if first.head <= newInterval.head =>
        Array(Math.min(first.head, newInterval.head), Math.max(first.last, newInterval.last)) :: next :: tail
      case (tail, next) => next :: tail
    }.toArray
  }

  val a = Array(Array(1, 3), Array(6, 9))
  println(insert(a, Array(2,5)).foreach(_.mkString(",")))
}
