package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 16/09/2020*/
object IsValidParenthesis extends App {
  def isValid(s: String): Boolean = {
    val len = s.length
    if(len <= 0 || len % 2 != 0) return false

    val stack = mutable.Stack[Char]()
    val mapping = Map[Char, Char](')' -> '(', '}' -> '{', ']' -> '[')

//    s.foreach(ch => {
//      if(mapping.contains(ch)) {
//        if(stack.isEmpty || mapping(ch) != stack.pop) return false
//      })
//      else stack.push(ch)
//    })
    stack.isEmpty
  }

  println(isValid("()"))
}
