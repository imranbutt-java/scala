package com.scala.learn.interview.leetcode

/* imransarwar created on 04/09/2020*/
object ReverseWords extends App {
  def reverseWords(s: String): String = {
    s.split(' ').toList.map(x => x.foldLeft(List[Char]())((x,y) => y :: x).mkString).mkString
  }
  println(reverseWords("Let's take LeetCode contest"))
}
