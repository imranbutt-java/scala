package com.scala.learn.interview.leetcode

/* imransarwar created on 08/09/2020*/
object SuggestProducts extends App {
  def suggestedProducts(products: Array[String], searchWord: String): List[List[String]] = {
    val sortedProducts = products.toList.sorted
    searchWord.zipWithIndex.foldLeft(List[List[String]]()){(suggestions, wordAndIndTuple) => wordAndIndTuple match {
      case (_, ind) => {
        val prefix = searchWord.take(ind + 1)
        suggestions :+ sortedProducts.filter(x => x.startsWith(prefix)).take(3)
      }
    }}
  }

  suggestedProducts(Array("mobile","mouse","moneypot","monitor","mousepad"),"mouse").foreach(x => {
    println
    x.foreach(y => print(s"$x,"))
  })
}
