package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 30/08/2020*/
object PalindromePartitioning extends App {
  def partition(s: String): List[List[String]] = {
    val ans = mutable.ListBuffer[List[String]]()
    var tmpAns = mutable.ListBuffer[String]()

    def isPalindrome(str: String): Boolean = str.reverse == str
    def helper(str: String): Unit = str match {
      case stt if(str.length == 0) => ans += tmpAns.toList
      case str =>
        for(k <- 0 until str.length) {
          if(isPalindrome(str.substring(0, k+1))) {
            tmpAns += str.substring(0,k+1)
            helper(str.substring(k+1))
            tmpAns.remove(tmpAns.size - 1)
          }
        }
    }

    helper(s)
    ans.toList.filter(x => !x.isEmpty)
  }

  partition("cdd").foreach(x => {
    print("[")
    x.foreach(y => print(s"$y,"))
    println("]")
  })
  println("abc".substring(1,2)+"...")
}
