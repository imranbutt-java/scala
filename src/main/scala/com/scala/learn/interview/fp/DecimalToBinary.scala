package com.scala.learn.interview.fp

/* imransarwar created on 12/08/2020*/
object DecimalToBinary extends  App {
  def decToBin(x: Int): String = {
    val seqOfDivByTwo = Iterator.iterate(x)(y => y/2)
    val binList = seqOfDivByTwo.takeWhile(y => y > 0).map(y => y % 2)
    binList.mkString.reverse
  }

  println("# Hello")
  println(decToBin(10))
}
