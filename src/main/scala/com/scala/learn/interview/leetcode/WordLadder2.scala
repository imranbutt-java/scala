package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 08/09/2020*/
object WordLadder2 extends App {
  def findLadders(beginWord: String, endWord: String, wordList: List[String]): List[List[String]] = {
    def isNeighbor(start: String, end: String): Boolean = {
      var count = 0
      for(i <- 0 until start.length) {
        if(start(i) != end(i)) count += 1
        if(count > 1) return false
      }
      count == 1
    }
    val wordGraph = (beginWord :: wordList).map(start => start -> wordList.filter(end => isNeighbor(start, end))).toMap
    val q = mutable.Queue[String]()
    val visited = mutable.Set[String]()

    val ans = mutable.ListBuffer[List[String]]()
    q.enqueue(beginWord)
    while(q.nonEmpty) {
      val level = q.size
      for(i <- 0 to level) {
        val word = q.dequeue
        if(word == endWord) return ans.toList

        visited += word
        val children = wordGraph(word).filterNot(visited)
        q ++= children
        ans += children
      }
    }
    List(List())
  }

  val begin = "hit"
  val end = "cog"
  val wordList = List("hot","dot","dog","lot","log","cog")
  findLadders(begin, end, wordList).foreach(x => {
    x.foreach(print)
    println
  })
}
