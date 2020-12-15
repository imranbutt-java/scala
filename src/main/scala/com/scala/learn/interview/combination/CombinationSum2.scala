package com.scala.learn.interview.combination

import scala.collection.mutable.ListBuffer
import scala.util.Sorting
import util.control.Breaks._


/* imransarwar created on 09/09/2020*/
object CombinationSum2 extends App {
  def combinationSum2(candidates: Array[Int], target: Int): List[List[Int]] = {
    val result = ListBuffer[List[Int]]()
    def dfs(remain: Int, start: Int, tmp: ListBuffer[Int]): Unit = {
      if(remain == 0) result += tmp.toList
      else if(remain > 0) {
        for(i <- start until candidates.size) {
          breakable {
            if (i > start && candidates(i) == candidates(i - 1)) break
            tmp.addOne(candidates(i))
            dfs(remain - candidates(i), i + 1, tmp)
            tmp.remove(tmp.size - 1)
          }
        }
      }
    }

    Sorting.quickSort(candidates)
    dfs(target, 0, ListBuffer())
    result.toList
  }

  combinationSum2(Array(10,1,2,7,6,1,5,-2,10),8).foreach(x => {
    x.foreach(y => print(s"$y,"))
    println
  })
}