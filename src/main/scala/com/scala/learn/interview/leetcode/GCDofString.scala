package com.scala.learn.interview.leetcode

/* imransarwar created on 18/08/2020*/
object GCDofString extends App {
  def gcdOfStrings(str1: String, str2: String): String = {
    if(str1.length < str2.length) {
      println(s"1. str1 = $str1 str2 = $str2")
      gcdOfStrings(str2, str1)
    }
    else if(!str1.startsWith(str2)) ""
    else if(str2.isEmpty) str1
    else {
      println(s"4. ${str1.substring(str2.length)}")
      gcdOfStrings(str1.substring(str2.length), str2)
    }
  }

  println(gcdOfStrings("ABABAB", "ABAB"))
}
