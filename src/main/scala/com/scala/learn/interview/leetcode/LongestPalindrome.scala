package com.scala.learn.interview.leetcode

/* imransarwar created on 19/08/2020*/
object LongestPalindrome extends App {
  def longestPalindrome(s: String): String = {
    val len = s.length - 1
    if(len < 1) return s

    var start = 0
    var left = 0
    var right = 0
    var palindromeLen = 0

    def findPalindrome(l: Int, r: Int) = {
      left = l; right = r
      while(left >= 0 && right < s.length && s.charAt(left) == s.charAt(right)) {
        left -= 1
        right += 1
      }
      if(palindromeLen < right - left - 1) {
        start = left + 1
        palindromeLen = right - left - 1
      }
    }

    for(ind <- 0 to len) {
      findPalindrome(ind, ind)
      findPalindrome(ind, ind+1)
    }
    s.substring(start, start + palindromeLen)
  }

  println(longestPalindrome("babad"))
}
