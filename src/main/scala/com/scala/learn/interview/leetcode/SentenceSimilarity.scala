package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 31/08/2020*/
object SentenceSimilarity extends App {
  def areSentencesSimilar(words1: Array[String], words2: Array[String], pairs: List[List[String]]): Boolean = {
    if(words1.length != words2.length) false
    else {
      val map = mutable.HashMap[String, Set[String]]().withDefault(x => Set[String]())
      for(p <- pairs) {
        map(p(0)) = map(p(0)) + p(1)
        map(p(1)) = map(p(1)) + p(0)
      }

      for((w1, i) <- words1.zipWithIndex) {
        if(!map.contains(w1)) return false
        if(!map(w1).contains(words2(i))) return false
      }
      true
    }
  }

    val words1 = Array("great","acting","skills")
    val words2 = Array("fine","drama","talent")
    val pairs = List(List("great","fine"),List("drama","acting"), List("skills","talent"))
  areSentencesSimilar(words1, words2, pairs)
}
