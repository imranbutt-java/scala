package com.scala.learn.interview.topKelement

/* imransarwar created on 06/01/2021*/
object TopKFrequentWord extends App {
  def topKFrequent(words: Array[String], k: Int): List[String] = {
    //words.groupBy(identity).toList.sortBy(s => (-s._2,s._1)).take(k).map(t=>t._1)
    words.foldLeft(Map[String,Int]().withDefaultValue(0))((map, word)=>
      map + (word -> (map(word) + 1))
    ).toList.sortBy(s => (-s._2,s._1)).take(k).map(_._1)
  }
}
