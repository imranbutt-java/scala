package com.scala.learn.interview.slidingwindow

import scala.collection.mutable

/* imransarwar created on 15/12/2020*/
/**
 * Given a string with lowercase letters only, if you are allowed to replace no more than ‘k’
 * letters with any letter, find the length of the longest substring having the same letters after replacement.
 *
 * Example 1:
 *
 * Input: String="aabccbb", k=2
 * Output: 5
 * Explanation: Replace the two 'c' with 'b' to have a longest repeating substring "bbbbb".
 */
object LongestSubstringWithSameLetterAfterReplacement extends App {
  def longSameLetterStrWithReplacement(str: String, k: Int): Int = {
    var maxStr = 0
    val chFreqMap = mutable.Map[Char, Int]().withDefaultValue(0)
    var left = 0
    var maxCharCount = 0

    for((ch, right) <- str.zipWithIndex) {
      chFreqMap.put(ch, chFreqMap(ch) + 1)
      maxCharCount = Math.max(maxCharCount, chFreqMap(ch))

      if(right - left + 1 - maxCharCount > k ) {
        chFreqMap.put(str(left), chFreqMap(str(left)) - 1)
        left += 1
      }
      maxStr = Math.max(maxStr, right - left + 1)
    }
    maxStr
  }

  println(longSameLetterStrWithReplacement("aabccbb", 2))
}
