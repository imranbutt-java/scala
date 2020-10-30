package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 10/09/2020*/
object NumberOfDistinctIsland extends App {
  def numDistinctIslands(grid: Array[Array[Int]]): Int = {
    val set = mutable.Set[String]()
    var sb = ""

    def isValidMove(r: Int, c: Int): Boolean = r >= 0 && r < grid.length && c >= 0 && c < grid(r).length && grid(r)(c) == 1

    def helper(path: String, row: Int, col: Int): Unit = {
      if(isValidMove(row, col)) {
        grid(row)(col) = 0
        sb += path
        helper("D", row + 1, col)
        helper("U", row - 1, col)
        helper("L", row, col - 1)
        helper("R", row, col + 1)
        sb += "X"
      }
    }

    for {
      i <- 0 until grid.length
      j <- 0 until grid(i).length
      if grid(i)(j) == 1
    } {
      sb = ""
      helper("X", i, j)
      set += sb
    }
    set.size
  }

  numDistinctIslands(Array( Array(1,1,0,0,0),
                            Array(1,1,0,0,0),
                            Array(0,0,0,1,1),
                            Array(0,0,0,1,1) ))
}
