package com.scala.learn.interview.leetcode

/* imransarwar created on 04/09/2020*/
object ReorderFiles extends App {
  def reorderLogFiles(logs: Array[String]): Array[String] = {
    val (dig, let) = logs.partition(_.split(' ').tail.head.head.isDigit)
    let.sortBy(_.span(_ != ' ').swap) ++ dig
  }

  val a = reorderLogFiles(Array("1 n u", "r 527", "j 893", "6 14", "6 82"))
  a.foreach(println)
}
