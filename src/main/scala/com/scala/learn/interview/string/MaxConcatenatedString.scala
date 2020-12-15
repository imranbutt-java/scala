package com.scala.learn.interview.string

/* imransarwar created on 25/11/2020*/
object MaxConcatenatedString extends App {
  def maxLength(arr: List[String]): Int = {
    val a = arr.filter(x => x.size == x.toSet.size)
    val b = a.combinations(arr.reduce(_+_).length)
    
    val n: List[String] = arr.filter(x => x.size == x.toSet.size)
      .combinations(arr.reduce(_+_).length)
      .filter(x => x.size == x.toSet.size).flatten.toList
    n.head.size
  }
  println(maxLength(List("un", "iq", "ue")))
}
