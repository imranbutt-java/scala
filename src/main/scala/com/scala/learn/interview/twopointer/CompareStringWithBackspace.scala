package com.scala.learn.interview.twopointer

/* imransarwar created on 20/12/2020*/
/**
 * Given two strings containing backspaces (identified by the character ‘#’), check
 * if the two strings are equal.
 *
 * Example 1:
 *
 * Input: str1="xy#z", str2="xzz#"
 * Output: true
 * Explanation: After applying backspaces the strings become "xz" and "xz" respectively.
 */
object CompareStringWithBackspace extends App {

  def compareStringWithBackspace(s1: String, s2: String): Boolean = {
    var i1 = s1.length - 1
    var i2 = s2.length - 1

    def nextValidIndex(s1: String, i1: Int): Int = {
      var validInd = i1
      var backspaceCount = 0
      while(validInd >= 0) {
        if(s1(validInd) == '#')     backspaceCount += 1
        else if(backspaceCount > 0) backspaceCount -= 1
        else return validInd
        validInd -= 1
      }
      validInd
    }

    while(i1 >= 0 || i2 >= 0) {
      val validInd1 = nextValidIndex(s1, i1)
      val validInd2 = nextValidIndex(s2, i2)

      if(validInd1 < 0 && validInd2 < 0) return true
      if(validInd1 < 0 || validInd2 < 0) return false
      if(s1(validInd1) != s2(validInd2)) return false

      i1 = validInd1 - 1
      i2 = validInd2 - 1
    }
    true
  }
  println(compareStringWithBackspace("xy#z", "xzz#"))
}
