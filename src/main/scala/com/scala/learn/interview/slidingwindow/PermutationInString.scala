package com.scala.learn.interview.slidingwindow

import scala.collection.mutable

/* imransarwar created on 15/12/2020*/
/**
 * Given a string and a pattern, find out if the string contains any permutation of the pattern.
 *
 * Permutation is defined as the re-arranging of the characters of the string. For example, “abc”
 * has the following six permutations:
 *
 * abc
 * acb
 * bac
 * bca
 * cab
 * cba
 * If a string has ‘n’ distinct characters, it will have n!n! permutations.
 *
 * Example 1:
 *
 * Input: String="oidbcaf", Pattern="abc"
 * Output: true
 * Explanation: The string contains "bca" which is a permutation of the given pattern.
 */
object PermutationInString extends App {
  def isAPermutation(str: String, pattern: String): Boolean = {
    var left = 0
    val chars = mutable.Map.empty ++ pattern.view.groupBy(identity).view.mapValues(_.size).toMap
    var required = chars.size

    for((ch, right) <- str.zipWithIndex) {
      if(chars.contains(ch)) {
        chars.put(ch, chars(ch) - 1)
        if(chars(ch) == 0)
          required -= 1
      }
      if(required == 0) return true

      if(right >= pattern.size - 1) {
        if(chars.contains(str(left))) {
          if(chars(str(left)) == 0)
            required += 1
          chars.put(str(left), str(left) + 1)
        }
        left += 1
      }
    }
    false
  }

  println(isAPermutation("oidbcaf", "abbc"))
}
