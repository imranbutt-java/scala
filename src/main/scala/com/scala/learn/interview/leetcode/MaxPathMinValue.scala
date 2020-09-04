package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 30/08/2020*/
object MaxPathMinValue extends App {
  def maximumMinimumPath(A: Array[Array[Int]]): Int = {
    val rows = A.length
    val cols = A(0).length

    var maxPathMinVal = Int.MaxValue
    val vist = Array.ofDim[Boolean](rows, cols)
    val prev = Array.ofDim[String](rows, cols)

    //value, i,j
    val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by(_._1))
    prev(0)(0) = 0+","+0
    pq.enqueue((A(0)(0), 0, 0))

    def isValidMove(i: Int, j: Int): Boolean = i >= 0 && i < rows && j >= 0 && j < cols && !vist(i)(j)

    val dir = Seq((1,0), (-1,0), (0,1), (0,-1))
    while(!pq.isEmpty) {
      val (v, i, j) = pq.dequeue
      vist(i)(j) = true

      maxPathMinVal = Math.min(maxPathMinVal, v)
      // Early exit
      if(i == rows - 1 && j == cols - 1)
        return maxPathMinVal

      for(d <- dir) {
        val newX = i + d._1
        val newY = j + d._2
        if(isValidMove(newX, newY)) {
          prev(newX)(newY) = i+","+j
          pq.enqueue((A(newX)(newY), newX, newY))
          vist(newX)(newY) = true
        }
      }
    }
    maxPathMinVal
  }

  val m = Array[Array[Int]](Array(5,4,5), Array(1,2,6), Array(7,100,6))
  maximumMinimumPath(m)
}
