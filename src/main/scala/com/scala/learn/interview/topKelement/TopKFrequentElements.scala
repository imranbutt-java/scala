package com.scala.learn.interview.topKelement

/* imransarwar created on 06/01/2021*/
object TopKFrequentElements extends App {
  def topKFrequent(nums: Array[Int], k: Int): Array[Int] = {
    nums.groupBy(identity).values.toArray.sortBy(_.size).map(_.head).take(k)
  }
  topKFrequent(Array(1,1,1,2,2,3), 2).foreach(println)
}
