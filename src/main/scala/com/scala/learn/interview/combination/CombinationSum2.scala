package com.scala.learn.interview.combination

/* imransarwar created on 09/09/2020*/
object CombinationSum2 extends App {
  def combinationSum2(candidates: Array[Int], target: Int): List[List[Int]] = {
    def combination(cand: List[Int], t: Int): List[List[Int]] = cand match {
      case _ if t < 0 => Nil
      case _ if t == 0 => List(Nil)
      case Nil => Nil
      case c :: cc => combination(cc, t-c).map(calculatedValue => c :: calculatedValue) ++ combination(cc, t)
    }
    combination(candidates.toList.sorted, target).distinct
  }
    combinationSum2(Array(10,1,2,7,6,1,5,-2,10),8).foreach(x => {
      x.foreach(y => print(s"$y,"))
      println
    })
}
