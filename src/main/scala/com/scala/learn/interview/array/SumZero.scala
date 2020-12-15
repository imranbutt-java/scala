package com.scala.learn.interview.array

/* imransarwar created on 24/11/2020*/
object SumZero extends App {
  def sumZero(n: Int): Array[Int] = {
    val ans = (0 until n/2).map(x => Array(-x, x)).flatten.toArray
    if(n % 2 != 0) ans :+ 0
    ans
  }
  sumZero(4).foreach(println)
}
