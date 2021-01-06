package com.scala.learn.interview.string
import scala.collection.mutable._
/* imransarwar created on 27/12/2020*/
object RearrangeStringKApart extends App {
  def rearrangeString(s: String, k: Int): String = {
    val map = Map[Char, Int]().empty ++ s.view.groupBy(identity).view.mapValues(_.size)
    val pq = PriorityQueue[Char]()(Ordering.fromLessThan[Char]((x, y) => if(map(x) == map(y)) x > y else map(x) < map(y)))

    map.keySet.foreach(pq.enqueue(_))

    val str = ListBuffer[Char]()
    while(pq.size >= k) {
      val list = ListBuffer[Char]()
      for(i <- 0 until k) {
        list.addOne(pq.dequeue)
        str.append(list(i))
      }
      for(i <- 0 until k) {
        map.put(list(i), map(list(i)) - 1)
      }
      for(i <- 0 until k) {
        if(map(list(i)) > 0) pq.enqueue(list(i))
      }
    }

    while(pq.nonEmpty) {
      if(map(pq.head) > 1) return ""
      str.addOne(pq.dequeue)
    }

    str.mkString
  }

  println(rearrangeString("aabbccdd", 3))
}
