package com.scala.learn.interview.bfs

import scala.collection.mutable.HashSet

/* imransarwar created on 16/08/2020*/

object WordBreak extends App {

  def wordBreak(s: String, wordDict: List[String],set: HashSet[String]): Boolean = {
    if (s.isEmpty) return true
    if(set.contains(s))
      return false;
    set.add(s)
    var flag = false
    wordDict.foreach(pre => if (s.startsWith(pre)) {
      flag |= wordBreak(s.substring(pre.length), wordDict,set)
    })
    flag
  }
  def wordBreak(s: String, wordDict: List[String]): Boolean = {//139, dp
    wordBreak(s,wordDict,new HashSet[String])
  }


//    val res = (1 to s.length).foldLeft(List(0)){
//      (acc, i) => {
//        println(acc +" , "+ i)
//        if(acc.exists(x => wordDict.contains(s.substring(x, i))))
//          i :: acc
//        else acc
//      }
//    }
//
//    res.head == s.length

    // Try to solve it
//    def wordBreak(s: String, wordDict: List[String],set: Set[String]): Boolean = {
//      if (s.isEmpty) return true
//      if(set.contains(s))
//        return false;
//      set += s
//      var flag = false
//      wordDict.foreach(pre => if (s.startsWith(pre)) {
//        flag |= wordBreak(s.substring(pre.length), wordDict,set)
//      })
//      flag
//    }
//
//    wordBreak(s,wordDict, mutable.HashSet[String])
//  }

  println(wordBreak("Leetcodeaa", List("Leet", "code", "aa")))
}
