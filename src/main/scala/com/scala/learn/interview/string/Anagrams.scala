package com.scala.learn.interview.string

/* imransarwar created on 18/08/2020*/
object Anagrams extends App {
  def areAnagrams(s: String, t: String): Boolean = {
    def charCountMap(str: String): Map[Char, Int] = str.view.groupBy(identity).view.mapValues(_.size).toMap
    charCountMap(s) == charCountMap(t)
  }

  println(areAnagrams("desert", "seredt"))
}
