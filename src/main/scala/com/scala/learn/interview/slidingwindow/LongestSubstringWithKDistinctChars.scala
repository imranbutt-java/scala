package com.scala.learn.interview.slidingwindow

import scala.collection.mutable

/* imransarwar created on 15/12/2020*/
/**
 * Given a string, find the length of the longest substring in it with no more than K distinct characters.
 *
 * Example 1:
 *
 * Input: String="araaci", K=2
 * Output: 4
 * Explanation: The longest substring with no more than '2' distinct characters is "araa".
 */
object LongestSubstringWithKDistinctChars extends App {
  def longestSubstring(str: String, k: Int): Int = {
    var left = 0
    var maxStr = 0
    val maxCharInd = mutable.Map[Char, Int]().withDefaultValue(0)

    for((ch, right) <- str.zipWithIndex) {
      maxCharInd.put(ch, maxCharInd(ch) + 1)

      while(maxCharInd.size > k) {
        val leftChar = str(left)
        maxCharInd.put(leftChar, maxCharInd(leftChar) - 1)
        if(maxCharInd(leftChar) == 0) maxCharInd.remove(leftChar)
        left += 1
      }
      maxStr = Math.max(maxStr, right - left + 1)
    }
    maxStr
  }

  println(longestSubstring("araaci", 2))
}
