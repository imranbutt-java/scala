package com.scala.learn.interview.fp

/* imransarwar created on 16/08/2020*/
object FindAnagrams extends App {
  def findAnagrams(s: String, p: String): List[Int] = {
    val map = p.view.groupBy(identity).view.mapValues(_.size)
    s.zipWithIndex.view.foldLeft(List.empty[Int])((acc, x) => {
      if (map.contains(x._1) && x._2 + p.length <= s.length) {
        val mapIdx = s.substring(x._2, x._2 + p.length).view.groupBy(identity).view.mapValues(_.size)
        if (map.toMap.equals(mapIdx.toMap)) acc :+ x._2 else acc
      } else {
        acc
      }
    })
  }

  println(findAnagrams("abab", "ab"))
}
