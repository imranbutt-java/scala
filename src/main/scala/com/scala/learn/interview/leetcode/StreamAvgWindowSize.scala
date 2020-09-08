package com.scala.learn.interview.leetcode

/* imransarwar created on 07/09/2020*/
object StreamAvgWindowSize extends App {
  import scala.collection.mutable
  class MovingAverage(_size: Int) {

    /** Initialize your data structure here. */
    val window = mutable.ArrayDeque[Int]()
    var sum = 0

    def next(`val`: Int): Double = {
      window.addOne(`val`)
      sum += `val`
      if(window.size > _size) {
        sum -= window.removeHead(true)
      }
      sum.toDouble / window.size.toDouble
    }
  }

  var obj = new MovingAverage(3)
  println(obj.next(1))
  println(obj.next(10))
  println(obj.next(3))
  println(obj.next(5))

}
