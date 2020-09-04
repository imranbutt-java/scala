package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 01/09/2020*/
object GroupAnagrams extends App {
  def groupAnagrams(strs: Array[String]): List[List[String]] = {
    val map = mutable.Map[String, List[String]]()
    strs.foreach(x => {
      val sorted = x.toSeq.sorted.toString
      if(map.contains(sorted)) map(sorted) = x :: map(sorted)
      else map.put(sorted, List(x))
    })
    map.values.toList
  }
  println(groupAnagrams(Array("tea", "eat", "tan", "ate", "nat", "bat")))
}
