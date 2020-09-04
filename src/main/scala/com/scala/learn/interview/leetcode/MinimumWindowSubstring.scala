package com.scala.learn.interview.leetcode

/* imransarwar created on 01/09/2020*/
object MinimumWindowSubstring extends App {
  def minWindow(s: String, t: String): String = {
    if (s.length == 0 || t.length == 0) {
      return ""
    }

    val map = collection.mutable.Map() ++ t.groupBy(c=>c).mapValues(_.length)
    var reqCh = t.length
    var start = 0
    var end = s.length - 1

    var left = 0
    var right = 0
    while(left <= right && right < s.length) {
      val ch = s.charAt(right)
      if(map.contains(ch)) {
        val chCount = map(ch)
        if(chCount > 0) reqCh -= 1
        map(ch) = chCount - 1
        // Found Solution
        if(reqCh == 0) {
          while(!map.contains(s.charAt(left)) || map.contains(s.charAt(left)) && map(s.charAt(left)) < 0) {
            if(map.contains(s.charAt(left))) map(s.charAt(left)) = map(s.charAt(left)) + 1
            left += 1
          }
          if((end - start) > (right - left)) {
            start = left
            end = right + 1
          }
          map(s.charAt(left)) = map(s.charAt(left)) + 1
          reqCh += 1
          left += 1
        }
      }
      right += 1
    }
    s.substring(start, end)
  }

  println(minWindow("ADOBECODEBANC", "ABC"))
}
