package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 24/08/2020*/
object KthSmallestElemInMatrix extends App {
  def kthSmallest(matrix: Array[Array[Int]], k: Int): Int = {
    val heap: mutable.PriorityQueue[Int] = mutable.PriorityQueue[Int]()(Ordering.Int)
    val set = mutable.HashSet[Int]()
    for {
      i <- matrix.indices
      j <- matrix(i).indices
    } {
      if(set.add(matrix(i)(j)))
        heap.enqueue(matrix(i)(j))
      println(s"[$i,$j]")
      if (heap.length > k) heap.dequeue
    }
    heap.dequeue
  }
  println(kthSmallest(Array( Array(7,8,8), Array(7,8,10), Array(11,12,13) ), 5))
}
