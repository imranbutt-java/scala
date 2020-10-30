package com.scala.learn.interview.array

import scala.collection.mutable

/* imransarwar created on 09/09/2020*/
object LongestSubStrWith2DistinctChar extends App {
  def lengthOfLongestSubstringTwoDistinct(s: String): Int = {
    if(s.length < 3) return s.length

    var left = 0
    var maxLen = 2
    val map = mutable.HashMap[Char, Int]()

    for((char, right) <- s.zipWithIndex) {
      map.put(char, right)

      if(map.size == 3) {
        val delIndex = map.minBy(_._2)._2
        map.remove(s.charAt(delIndex))
        left = delIndex + 1
      }
      maxLen = Math.max(maxLen, right - left + 1)
    }
    maxLen
  }

  println(lengthOfLongestSubstringTwoDistinct("ececba"))
}
