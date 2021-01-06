package com.scala.learn.interview.stack
import scala.collection.mutable._
import util.control.Breaks._

/* imransarwar created on 31/12/2020*/
object NextGreaterElement3 extends App {
  def nextGreaterElement(n: Int): Int = {
    val nums = n.toString.toArray

    var defPoint = nums.length - 1
    var firstSwapInd = 0
    var secondSwapInd = 0

    def swap(): Unit = {
      val tmp = nums(firstSwapInd)
      nums(firstSwapInd) = nums(secondSwapInd)
      nums(secondSwapInd) = tmp
    }
    def reverse(): Unit = {
      val reversed = nums.splitAt(firstSwapInd+1)._2.mkString.reverse.toArray
      for(i <- 0 until reversed.length) {
        val ind = nums.length - reversed.length + i
        nums(ind) = reversed(i)
      }
    }

    breakable {
      while(defPoint > 0) {
        if(nums(defPoint-1) < nums(defPoint)) break
        defPoint -= 1
      }
    }
    // Descending order of digits
    if(defPoint == 0) return -1

    firstSwapInd = defPoint - 1
    secondSwapInd = defPoint

    while(secondSwapInd < nums.length-1 && nums(secondSwapInd) > nums(firstSwapInd))
      secondSwapInd += 1

    swap()
    reverse()
    nums.mkString.toInt
  }
  println(nextGreaterElement(12))
}
