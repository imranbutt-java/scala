package com.scala.learn.interview.leetcode

/* imransarwar created on 04/09/2020*/
object ReverseString extends App {
  def reverseStr(s: String, k: Int): String = {
    // ab cd ef g
    // 0  1  2  3
    // reverse ab and ef
    s.toSeq.sliding(k,k).zipWithIndex.map{
      case (v,i) if i % 2 == 0 => v.reverse
      case (v,_) => v
    }.mkString("")
  }

  println(reverseStr("abcdefg", 2))
}
