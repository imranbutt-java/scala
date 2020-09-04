package com.scala.learn.interview.leetcode

/* imransarwar created on 04/09/2020*/
object FirstUniqueChar extends App {
  def firstUniqChar(s: String): Int = {
    val map = s.zipWithIndex.groupBy(_._1).view.mapValues(_.length)
    for((ch, i) <- s.zipWithIndex) {
      if(map(ch) == 1) return i
    }
    -1
  }
  println(firstUniqChar("eetlcode"))
}
