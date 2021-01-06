package com.scala.learn.interview.bitwise

/* imransarwar created on 04/01/2021*/
object MinimumFlips extends App {
  def minFlips(a: Int, b: Int, c: Int): Int = {
    var res = 0
    var n1 = a
    var n2 = b
    var n3 = c
    while(n1 != 0 || n2 != 0 || n3 != 0) {
      println(s"N: $n1, $n2, $n3")
      val r1 = n1%2
      val r2 = n2%2
      val r3 = n3%2

      println(s"R: $r1, $r2, $r3")

      if(r3 == 0) res += (r1 + r2)
      else if(r1 + r2 == 0) res += 1

      n1 /= 2
      n2 /= 2
      n3 /= 2
    }
    res
  }
  println(minFlips(2, 6, 5))
}
