package com.scala.learn.interview.slidingwindow

/* imransarwar created on 15/12/2020*/
/**
 * Given a string, find the length of the longest substring, which has no repeating characters.
 *
 * Example 1:
 *
 * Input: String="aabccbb"
 * Output: 3
 * Explanation: The longest substring without any repeating characters is "abc".
 */
object NoRepeatSubstring extends App {
  def noRepeatSubstring(str: String): Int = {
    val chars = Array.fill(26)(-1)
    var left = 0
    var maxStr = 0

    for((ch, right) <- str.zipWithIndex) {
      val chInd = ch - 'a'

      if(chars(chInd) != -1)
        left = Math.max(left, chars(chInd) + 1)
        
      chars(chInd) = right
      maxStr = Math.max(maxStr, right - left + 1)
    }
    maxStr
  }
  println(noRepeatSubstring("aabccbb"))
}
