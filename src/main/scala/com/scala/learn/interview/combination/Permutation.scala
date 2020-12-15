package com.scala.learn.interview.combination

/* imransarwar created on 24/11/2020*/
object Permutation extends App {
  def permute(nums: Array[Int]): List[List[Int]] = {
    def permutate(set: Set[Int]): Set[List[Int]] = set match {
      case s if s.isEmpty => Set(Nil)
      case s => for {
        n <- s
        p <- permutate(s - n)
      } yield n :: p
    }
    permutate(nums.toSet).toList
  }
  permute(Array(1,2,3)).foreach(println)
}
