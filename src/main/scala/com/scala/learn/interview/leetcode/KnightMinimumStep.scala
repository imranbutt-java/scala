package com.scala.learn.interview.leetcode

import scala.collection.mutable._

/* imransarwar created on 29/07/2020*/
object KnightMinimumStep extends App {
  def minKnightMoves(x: Int, y: Int): Int = {
    val dir = List((2, 1), (1, 2), (-1, 2), (-2, 1), (-2, -1), (-1, -2), (1, -2), (2, -1))
    val queue: Queue[(Int, Int)] = new Queue
    queue.enqueue((0,0))
    var count = 0
    var set: HashSet[String] = new HashSet

    while(queue.nonEmpty) {
      count += 1
      val positionInSingleHop = queue.length
      for(s <- 1 to positionInSingleHop) {
        val currentPos = queue.dequeue

        if(currentPos._1 == x && currentPos._2 == y)
          return count
        else
          for(d <- dir) {
            val newX = currentPos._1 + d._1
            val newY = currentPos._2 + d._2
            if(!set.contains(newX+"-"+newY) && newX >= -1 && newY >= -1) {
              set += (newX+"-"+newY)
              queue.enqueue((newX, newY))
            }
          }
      }
    }
    count
  }

  println(minKnightMoves(5,5))
}


