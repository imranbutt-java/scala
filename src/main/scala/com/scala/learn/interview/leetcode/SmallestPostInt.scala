package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 07/09/2020*/
object SmallestPostInt extends App {
  def solution(a: Array[Int]): Int = {
    // write your code in Scala 2.12
    val pq = mutable.PriorityQueue[Int]()(Ordering.by(x=>x)).reverse
    for((n, i) <- a.toSet.filter(_ > 0).zipWithIndex) {
      pq.enqueue(n)
    }
    for(i <- 1 to a.max) {
      if(pq.isEmpty) return i
      else if (pq.dequeue > i) return i
    }
    -1
  }
  println(solution(Array(1,3,6,4,1,2)))
}
