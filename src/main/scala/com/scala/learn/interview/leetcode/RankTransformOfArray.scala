package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 08/10/2020*/
object RankTransformOfArray extends App {
  def arrayRankTransform(arr: Array[Int]): Array[Int] = {
    var rank = 1
    val set = mutable.TreeSet[Int]()
    val map = mutable.Map[Int, Int]()
    arr.foreach(x => set.add(x))
    set.foreach(x => {
      map.put(x, rank)
      rank += 1
    })

    val ranks = Array.ofDim[Int](arr.length)
    for(i <- 0 until arr.length) {
      ranks(i) = map(arr(i))
    }
    ranks
  }
  arrayRankTransform(Array(40,10,20,30)).foreach(println)
}
