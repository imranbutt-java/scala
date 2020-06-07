package com.scala.learn.interview.numbers

/* imransarwar created on 29/05/2020*/
object Decompose extends App {
  def lengthOfLongestSubstring(s: String): Int = {
    val tmp = s.scanLeft("")((x: String, y: Char) => x.substring(1 + x.indexOf(y)) + y)
    println(tmp)
      tmp.map(_.length).reduce(Math.max)
  }

//  val a = " ab"
//  println(a.substring(0,3).contains(b))
//  println(a.length)
  println(lengthOfLongestSubstring("acba "))
}
