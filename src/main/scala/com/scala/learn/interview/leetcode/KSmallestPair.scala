package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 24/08/2020*/
object KSmallestPair extends App {
  def kSmallestPairs(nums1: Array[Int], nums2: Array[Int], k: Int): List[List[Int]] = {
    val minHeap = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by(_._1))

    for {
      i <- nums1
      j <- nums2
    } {
      minHeap.enqueue((i + j, i, j))
      if(minHeap.size > k) minHeap.dequeue
    }
    minHeap.dequeueAll[(Int, Int, Int)].map(x => List(x._2, x._3)).toList.reverse
  }
  println(kSmallestPairs(Array(1,7,11), Array(2,4,6), 3))
}
