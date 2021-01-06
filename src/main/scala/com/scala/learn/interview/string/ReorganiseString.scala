package com.scala.learn.interview.string

import scala.collection.mutable

/* imransarwar created on 22/12/2020*/
object ReorganiseString extends App {
  def reorganizeString(S: String): String = {
    val map = mutable.Map[Char, Int]().empty ++ S.view.groupBy(identity).view.mapValues(_.size)
    val pq = mutable.PriorityQueue[Char]()(Ordering.fromLessThan[Char](map(_) < map(_)))

    map.keySet.foreach(pq.enqueue(_))

    val s = mutable.ListBuffer[Char]()
    while(pq.size > 1) {
      val curr = pq.dequeue
      val next = pq.dequeue

      s.addOne(curr)
      s.addOne(next)

      map.put(curr, map(curr) - 1)
      map.put(next, map(next) - 1)

      if(map(curr) > 1) pq.enqueue(curr)
      if(map(next) > 1) pq.enqueue(next)
    }

    if(pq.nonEmpty) {
      val ch = pq.dequeue
      if(map(ch) > 1) return ""
      s.addOne(ch)
    }
    s.mkString
  }
}
