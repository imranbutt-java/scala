package com.scala.learn.interview.string

import scala.collection.mutable
import scala.collection.mutable.StringBuilder

/* imransarwar created on 08/12/2020*/
object BalancedString2 extends App {
  def balancedString(str: String): String = {
    var slow = 0
    var tmp = new StringBuilder(str.substring(0, 1))

    def isBalanced(s: String): Boolean = {
      if(s.length == 0) return false
      val chars = Array.ofDim[Int](26)
      for(ch <- s.filter(_.isLower).toSet.mkString) {
        chars(ch - 'a') += 1
      }
      for(ch <- s.filter(_.isUpper).toSet.mkString) {
        chars(ch - 'A') -= 1
      }
      chars.filter(_ != 0).size == 0
    }
    var res = new StringBuilder(str :+ 'A')

    for(f <- 1 until str.length) {
      for(fast <- f until str.length) {
        tmp.append(str(fast))
        while (isBalanced(tmp.toString())) {
          println(s"Balanced => $tmp")
          if (res.size > tmp.size) {
            res = new StringBuilder(tmp.mkString)
            println(s"Upated $res")
          }
          tmp.deleteCharAt(0)
          println(s"After Removing => $tmp")
          slow += 1
        }
      }
      slow += f
      tmp = new StringBuilder()
    }
    if(res.size > str.size) "-1" else res.mkString
  }

  println(balancedString("TacoCat"))
}
