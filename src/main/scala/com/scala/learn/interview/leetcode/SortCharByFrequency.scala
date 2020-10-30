package com.scala.learn.interview.leetcode

import scala.collection.immutable.ListMap

/* imransarwar created on 04/09/2020*/
object SortCharByFrequency extends App {
  def frequencySort(s: String): String = {
    val map = s.zipWithIndex.groupBy(_._1).view.mapValues(_.length)
    val sortByValue = ListMap(map.toSeq.sortWith(_._2 > _._2):_*)
    sortByValue.map(x => s"${x._1}" * x._2).mkString
  }
  println(frequencySort("tree"))
}
