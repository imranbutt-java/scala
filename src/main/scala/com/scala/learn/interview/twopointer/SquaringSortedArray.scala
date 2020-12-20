package com.scala.learn.interview.twopointer

/* imransarwar created on 19/12/2020*/
/**
 * Given a sorted array, create a new array containing squares of all the numbers of the input array
 * in the sorted order.
 *
 * Example 1:
 *
 * Input: [-2, -1, 0, 2, 3]
 * Output: [0, 1, 4, 4, 9]
 */
object SquaringSortedArray extends App {
  def squaringSortedArray(nums: Array[Int]): Array[Int] = {
    var left = 0
    var right = nums.length - 1
    var ind = right
    val ans = Array.ofDim[Int](nums.length)
    while(left <= right) {
      val leftSquare = nums(left) * nums(left)
      val rightSquare = nums(right) * nums(right)

      if(leftSquare > rightSquare) {
        ans(ind) = leftSquare
        left += 1
      } else {
        ans(ind) = rightSquare
        right -= 1
      }
      ind -= 1
    }
    ans
  }

  squaringSortedArray(Array(-2, -1, 0, 2, 3)).foreach(println)

  assert(squaringSortedArray(Array(-2, -1, 0, 2, 3)) sameElements Array(0, 1, 4, 4, 9))
}
