package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 20/08/2020*/
object IsomorphicString extends App {
  def isIsomorphic(s: String, t: String): Boolean = {
    val mapS = mutable.HashMap[Char,Char]()
    val mapT = mutable.HashMap[Char,Char]()
    if(s.length != t.length) return false

    for((str, ind) <- s.zipWithIndex) {
      if(mapS.contains(str) && mapS(str) != t.charAt(ind)) return false
      else if(mapT.contains(t.charAt(ind)) && mapT(t.charAt(ind)) != str) return false
      mapS += (str -> t.charAt(ind))
      mapT += (t.charAt(ind) -> str)
    }
    true
  }

  println(isIsomorphic("ab", "aa"))
}
