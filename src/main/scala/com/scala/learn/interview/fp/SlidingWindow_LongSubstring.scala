package com.scala.learn.interview.fp

/* imransarwar created on 14/08/2020*/
object SlidingWindow_LongSubstring extends App {
  def lengthOfLongestSubstring(s: String): Int = {
    var left = 0
    var max = 0
    // Define a map where key is the character and element is index
    // This way we can update the index to where a character is repeated
    var indices: Map[Char,Int] = Map.empty

    for((char, right) <- s.zipWithIndex) {
      if(indices contains char)
        //If character is repeated, move start to one ahead of repeated character.
        //Take max to make sure we dont go backwards:
        left = Math.max(left, indices(char)+1)
      indices += (char -> right)
      max = Math.max(max, right - left + 1)
    }
    max
  }

  println(lengthOfLongestSubstring("abcbcbb"))
}
