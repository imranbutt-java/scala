package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 07/09/2020*/
object WordLadder extends App {
  def ladderLength(beginWord: String, endWord: String, wordList: List[String]): Int = {
    def isNeighbour(start: String, end: String): Boolean = {
      if(start.length != end.length) return false
      var count = 0
      for(i <- 0 until start.length) {
        if(end(i) != start(i)) count += 1
        if(count > 1) return false
      }
      count == 1
    }
    val wordGraph = (beginWord :: wordList).map(start => start -> wordList.filter(end => isNeighbour(start, end))).toMap
    val visited = mutable.Set[String]()
    val q = mutable.Queue[String]()

    var level = 0
    q.enqueue(beginWord)
    while(q.nonEmpty) {
      level += 1
      val neighbor = q.size
      for(i <- 0 to neighbor) {
        val word = q.dequeue
        if(word == endWord) return level

        visited += word
        val children = wordGraph(word).filterNot(visited)
        q ++= children
      }
    }
    0
  }
}
