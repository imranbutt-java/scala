package com.scala.learn.interview.design

/* imransarwar created on 14/09/2020*/

import scala.collection.mutable

class FirstUnique(_nums: Array[Int]) {
  //(num, count)
  val linkedMap = mutable.LinkedHashMap[Int, Int]().withDefaultValue(0)
  for (num <- _nums)
    linkedMap.put(num, linkedMap(num) + 1)

  def showFirstUnique(): Int = {
    linkedMap.find(x => x._2 == 1).getOrElse((-1, -1))._1
  }

  def add(value: Int) {
    linkedMap.put(value, linkedMap(value) + 1)
  }

}
