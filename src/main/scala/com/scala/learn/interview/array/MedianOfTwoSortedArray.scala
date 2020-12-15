package com.scala.learn.interview.array

/* imransarwar created on 23/11/2020
*
* https://leetcode.com/problems/median-of-two-sorted-arrays/
* */
object MedianOfTwoSortedArray extends App {
  def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
    val d = (nums1 ++ nums2).sorted
    val len = d.size

    val x = d(len / 2)
    val y = d((len - 2) / 2)
    val z = x + y

    var acc = z / 2F
    if(d.size % 2 == 0) acc
    else if(d.size == 1) d(0) else d((d.size - 1) / 2)
  }
}
