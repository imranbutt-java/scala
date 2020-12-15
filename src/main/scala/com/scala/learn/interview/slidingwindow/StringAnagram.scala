package com.scala.learn.interview.slidingwindow

import scala.collection.mutable

/* imransarwar created on 15/12/2020*/
/**
 * Given a string and a pattern, find all anagrams of the pattern in the given string.
 *
 * Anagram is actually a Permutation of a string. For example, “abc” has the following six anagrams:
 *
 * abc
 * acb
 * bac
 * bca
 * cab
 * cba
 * Write a function to return a list of starting indices of the anagrams of the pattern in the given string.
 *
 * Example 1:
 *
 * Input: String="ppqp", Pattern="pq"
 * Output: [1, 2]
 * Explanation: The two anagrams of the pattern in the given string are "pq" and "qp".
 */
object StringAnagram extends App {
  def findAnagram(str: String, pattern: String): Array[Int] = {
    var left = 0
    val countMap = mutable.Map.empty ++ pattern.view.groupBy(identity).view.mapValues(_.size).toMap
    val indicies = mutable.ListBuffer.empty[Int]
    var required = countMap.size

    for((ch, right) <- str.zipWithIndex) {
      if(countMap.contains(ch)) {
        countMap.put(ch, countMap(ch) - 1)
        if(countMap(ch) == 0) required -= 1
      }
      if(required == 0) indicies.addOne(left)

      if(right >= pattern.size - 1) {
        val leftChar = str(left)
        if(countMap.contains(leftChar)) {
          if(countMap(leftChar) == 0) required += 1
          countMap.put(leftChar, countMap(leftChar) + 1)
        }
        left += 1
      }
    }
    indicies.toArray
  }
  findAnagram("ppqp", "pq").foreach(println)
}
