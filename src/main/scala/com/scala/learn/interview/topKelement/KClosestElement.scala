package com.scala.learn.interview.topKelement

import scala.collection.mutable._

/* imransarwar created on 29/12/2020*/
object KClosestElement extends App {
  def findClosestElements(arr: Array[Int], k: Int, x: Int): List[Int] = {
    case class Entry(num: Int, diff: Int)
    def binarySearch(_left: Int, _right: Int): Int = {
      var left = _left
      var right = _right
      while(left <= right) {
        val mid = left + (right - left) / 2
        if(arr(mid) == x) return mid

        if(arr(mid) < x) left = mid + 1
        else right = mid - 1
      }
      if(left > 0) return left - 1
      left
    }

    val mid = binarySearch(0, arr.length - 1)
    val low = Math.max(mid - k, 0)
    val high = Math.min(mid + k, arr.length - 1)

    val minHeap = PriorityQueue[Entry]()(Ordering.fromLessThan((a,b) => if(a.diff == b.diff) a.num > b.num else a.diff > b.diff))

    for(i <- low to high) minHeap.enqueue(Entry(arr(i), Math.abs(arr(i) - x)))

    val ans = ListBuffer[Int]()
    for(i <- 0 until k) ans.addOne(minHeap.dequeue.num)
    ans.toList.sorted
  }

  println(findClosestElements(Array(0,0,1,2,3,3,4,7,7,8), 3, 5).mkString(","))
}
