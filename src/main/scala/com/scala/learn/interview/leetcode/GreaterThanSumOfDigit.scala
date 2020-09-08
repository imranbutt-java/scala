package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 07/09/2020*/
object GreaterThanSumOfDigit extends App {
  def solution(n: Int): Int = {
    // write your code in Scala 2.12
    def sumOfDigit(n: Int): Int = n.toString.map{ _.asDigit }.sum
    val sum = sumOfDigit(n)
    var start = n + 1
    var counter = sum
    while(counter != 0) {
      while(sumOfDigit(start) != sum) start += 1
      counter -= 1
    }
    start
  }
  println(solution(28))
}
