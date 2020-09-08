package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 08/09/2020*/
object FreqStack extends App {
  class FreqStack() {
    //tuple item 1 is the number,
    //tuple item 2 is the count,
    //tuple item 3 is the operation count - needed as a tiebreaker for the count
    val pq: mutable.PriorityQueue[(Int, Int, Int)] = mutable.PriorityQueue[(Int, Int, Int)]()(
      Ordering.fromLessThan((item1, item2) => {
        if (item1._2 < item2._2) true
        else if (item2._2 < item1._2) false
        else if (item1._3 < item2._3) true
        else false
      })
    )

    //maintains how many times a number is in our list.
    val numberMap: mutable.Map[Int, Int] = mutable.Map[Int, Int]().withDefaultValue(0)
    var operationCount: Int = 0

    def push(x: Int): Unit = {
      operationCount = operationCount + 1
      numberMap.put(x, numberMap(x) + 1)
      pq.enqueue((x, numberMap(x), operationCount))
    }

    def pop(): Int = {
      val returnValue: Int = pq.dequeue()._1
      numberMap(returnValue) = numberMap(returnValue) - 1
      returnValue
    }
  }
}
