package com.scala.learn.interview.slidingwindow

import scala.collection.mutable

/* imransarwar created on 15/12/2020*/
/**
 * Given an array of characters where each character represents a fruit tree, you are given two baskets,
 * and your goal is to put maximum number of fruits in each basket. The only restriction is that each basket
 * can have only one type of fruit.
 *
 * You can start with any tree, but you can’t skip a tree once you have started. You will pick one
 * fruit from each tree until you cannot, i.e., you will stop when you have to pick from a third fruit type.
 *
 * Write a function to return the maximum number of fruits in both the baskets.
 *
 * Example 1:
 *
 * Input: Fruit=['A', 'B', 'C', 'A', 'C']
 * Output: 3
 * Explanation: We can put 2 'C' in one basket and one 'A' in the other from the subarray ['C', 'A', 'C']
 */
object FruitsIntoBaskets extends App {
  def fruitsIntoBaskets(fruits: Array[Char]): Int = {
    val baskets = mutable.Map[Char, Int]().withDefaultValue(0)
    var left = 0
    var maxFruit = 0

    for((fruit, tree) <- fruits.zipWithIndex) {
      baskets.put(fruit, baskets(fruit) + 1)

      if(baskets.size > 2) {
        val leftFruit = fruits(left)
        baskets.put(leftFruit, baskets(leftFruit) - 1)
        if(baskets(leftFruit) == 0) baskets.remove(leftFruit)
        left += 1
      }
      maxFruit = Math.max(maxFruit, tree - left + 1)
    }
    maxFruit
  }
  println(fruitsIntoBaskets(Array('A', 'B', 'C', 'A', 'C')))
}
