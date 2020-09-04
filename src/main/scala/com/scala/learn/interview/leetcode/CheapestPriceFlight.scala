package com.scala.learn.interview.leetcode

import scala.collection.mutable

//https://leetcode.com/problems/cheapest-flights-within-k-stops/solution/
/* imransarwar created on 22/08/2020*/
object CheapestPriceFlight extends App {
  def findCheapestPrice(n: Int, flights: Array[Array[Int]], src: Int, dst: Int, K: Int): Int = {
    val E = flights.groupMap(f => f(0))(f => (f(1), f(2)))
    val minHeap = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by(_._1)).reverse

    minHeap.enqueue((0, src, K+1))
    while(!minHeap.isEmpty) {
      val (price, sr, stop) = minHeap.dequeue
      if(sr == dst) return price
      if(stop > 0 && E.contains(sr)) {
        for(arr <- E(sr)) minHeap.enqueue( (price + arr._2, arr._1, stop-1) )
      }
    }
    -1
  }

  println(findCheapestPrice(5, Array( Array(4,1,1),Array(1,2,3),Array(0,3,2),Array(0,4,10),Array(3,1,1),Array(1,4,3)), 2, 1, 1))

}
