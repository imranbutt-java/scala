package com.scala.learn.interview.fp

/* imransarwar created on 26/12/2020*/
object Combinations extends App {
  def combine(n: Int, k: Int): List[List[Int]] = {
    def combination[A](sol: List[A], k: Int): List[List[A]] = k match {
      case 0 => List(Nil)
      case k => sol match {
        case Nil => List()
        case head :: tail => combination(tail, k) ++ combination(tail, k - 1).map(head :: _)
      }
    }
    combination((1 to n).toList, k)
  }

  combine(4, 2).foreach(println)
}
