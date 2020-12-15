package com.scala.learn.interview.string

import scala.collection.immutable.WrappedString

/* imransarwar created on 24/11/2020*/
object PalindromePermutation extends App {

  def canPermutePalindrome(s: String): Boolean = {
    def isPalindrome(x: String): Boolean = {
      var left = 0
      var right = x.length - 1
      while(left <= right) {
        if(x(left) != x(right))
          return false
        left += 1
        right -= 1
      }
      true
    }

    !s.toSeq.combinations(s.length).filter(x => isPalindrome(x.toString())).isEmpty
  }

  println(canPermutePalindrome("aab"))
}
