package com.scala.learn.interview.leetcode

/* imransarwar created on 07/09/2020*/
object FindMatrixFromSum extends App {
  def solution(u: Int, l: Int, c: Array[Int]): String = {
    // write your code in Scala 2.12

    var uCounter = u
    var lCounter = l
    var firstRow = ""
    var secondRow = ""
    c.toList match {
      case v :: xs if v == 2 => {
        firstRow += "1"
        secondRow += "1"
        uCounter -= 1
        lCounter -= 1
      }
      case v :: xs if v == 1 && uCounter > 0 => {
        firstRow += "1"
        secondRow += "0"
        uCounter -= 1
      }
      case v :: _ if v == 1 && lCounter > 0 => {
        firstRow += "1"
        secondRow += "0"
        uCounter -= 1
      }
      case _ => {
        firstRow += "0"
        secondRow += "0"
      }
    }

    if(uCounter < 0 || lCounter < 0) return "IMPOSSIBLE"
    firstRow+","+secondRow
  }

  println(solution(2, 3, Array(0,0,1,1,2)))
}
