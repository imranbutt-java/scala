package com.scala.learn.interview.array

/* imransarwar created on 04/12/2020*/
object MergeIntervals extends App {
  def merge(intervals: Array[Array[Int]]): Array[Array[Int]] = {
    intervals.sortBy(_(0)).foldLeft(List[Array[Int]]()) {
      case (f :: xs, s) if f(1) >= s(0) && s(0) >= f(0) =>
        Array(f(0), Math.max(s(1), f(1))) :: xs
      case (xs, s) => s :: xs
    }.toArray
  }


}
