package com.scala.learn.interview.array

import scala.collection.mutable

/* imransarwar created on 30/11/2020*/
object LengthOfLongestSubstringKDistinct extends App {
  def lengthOfLongestSubstringKDistinct(s: String, k: Int): Int = {
    val n = s.length
    if(n * k == 0) return 0

    val map = mutable.LinkedHashMap[Char, Int]().withDefaultValue(0)
    var left = 0
    var right = 0

    var maxLen = 1
    while(right < n) {
      val ch = s(right)
      // Delete it and add again with index, so that it be at last order
      if(map.contains(ch))
        map.remove(ch)

      map.addOne(ch, right)
      right += 1

      if(map.size == k+1) {
        // Delete Left Most
        left = map.remove(map.head._1).get + 1
      }
      maxLen = Math.max(maxLen, right - left)
    }
    maxLen
  }

  lengthOfLongestSubstringKDistinct("bacc", 2)
}
