package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 07/09/2020*/
object WordBreak2 extends App {
  def wordBreak(s: String, wordDict: List[String]): List[String] = {

    def wordBreak(str: String, memo: mutable.Map[String, List[String]]): List[String] = str match {
      case str if memo.contains(str) => memo(str)
      case str if str.length == 0 => List("")
      case str => {
        val ans = mutable.ListBuffer[String]()
        for(word <- wordDict) {
          if(str.startsWith(word)) {
            val tmp = wordBreak(str.substring(word.length), memo)
            for(sub <- tmp) {
              val space = if(sub.isEmpty) "" else " "
              ans.addOne(word+ space + sub)
            }
          }
        }
        memo.put(str, ans.toList)
        ans.toList
      }
    }
    wordBreak(s, mutable.Map[String, List[String]]())
  }


  val s = "catsanddog"
  val dict = List("cat","cats","and","sand","dog")
  wordBreak(s, dict).foreach(println)
}
