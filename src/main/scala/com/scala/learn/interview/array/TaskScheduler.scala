package com.scala.learn.interview.array

/* imransarwar created on 25/12/2020*/
object TaskScheduler extends App {
  def leastInterval(tasks: Array[Char], n: Int): Int = {
    val chars = tasks.groupBy(identity).map(_._2.size).toSeq.sortBy(-_)
    val nmax = chars.takeWhile(_ == chars.head).size
    math.max(tasks.size, (chars.head-1) * (n+1)+nmax)
  }
}
