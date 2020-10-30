package com.scala.learn.interview.fp

/* imransarwar created on 11/10/2020*/
/*
We have a garden and want to cover with fence.
Long length fence has less cost,
So buy long length fence first to get minimum cost.
Greedy Algorithm
 */
object GreedyAlgoGardenNets extends App {
  case class Selection(perimeter: Int, gardenNets: List[Int])

  def selectNets(perimeter: Int, netLengths: Array[Int]): List[Int] = {
    val finalSelection = netLengths.sorted.reverse.foldLeft(Selection(perimeter, List())) {
      (selection, length) => {
        val numbers = selection.perimeter / length
        val netsToBuy = List.fill(numbers)(length)
        Selection(selection.perimeter - numbers * length, selection.gardenNets ::: netsToBuy)
      }
    }
    finalSelection.gardenNets
  }

  val gardenNetsLengths = Array(10, 3, 2, 1, 15)
//  val gardenNetsLengths = Array(10, 5, 4, 3, 15)
  val permimeter = 50
  println(selectNets(permimeter, gardenNetsLengths))
}
