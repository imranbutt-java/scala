package com.scala.learn.interview.twoheaps

import scala.collection.mutable._

/* imransarwar created on 25/12/2020*/
/**
 * Given an array of numbers and a number ‘k’, find the median of all the ‘k’ sized
 * sub-arrays (or windows) of the array.
 *
 * Example 1:
 *
 * Input: nums=[1, 2, -1, 3, 5], k = 2
 * Output: [1.5, 0.5, 1.0, 4.0]
 * Explanation: Lets consider all windows of size ‘2’:
 *
 * [1, 2, -1, 3, 5] -> median is 1.5
 * [1, 2, -1, 3, 5] -> median is 0.5
 * [1, 2, -1, 3, 5] -> median is 1.0
 * [1, 2, -1, 3, 5] -> median is 4.0
 */
object SlidingWindowMedian extends App {
  def medianSlidingWindow(nums: Array[Int], k: Int): Array[Double] = {
    val window = Array.ofDim[Int](k)
    val minHeap = PriorityQueue[Int]()(Ordering.by(identity)).reverse
    val maxHeap = PriorityQueue[Int]()(Ordering.by(identity))
    val ans = ListBuffer[Double]()

    // On moving to next window adjust min/max heap
    def rebalanceHeaps(num: Int): Unit = {
      if(maxHeap.size - minHeap.size > 1) minHeap.enqueue(maxHeap.dequeue)
      else if(maxHeap.size < minHeap.size) maxHeap.enqueue(minHeap.dequeue)
    }

    def addToHeap(num: Int): Unit = {
      if(maxHeap.isEmpty || maxHeap.head >= num) maxHeap.enqueue(num)
      else minHeap.enqueue(num)
    }

    def findMedian(): Unit = {
      val median = if(maxHeap.size == minHeap.size) maxHeap.head / 2.0 + minHeap.head / 2.0
      else maxHeap.head.toDouble

      ans.addOne(median)
    }

    def removeNum(num: Int): Unit = {
      val tmp = ListBuffer[Int]()
      def remove(heap: PriorityQueue[Int]): Unit = {
        while(heap.nonEmpty && heap.head != num)
          tmp.addOne(heap.dequeue)
        heap.dequeue
        tmp.foreach(x => heap.enqueue(x))
      }

      if(maxHeap.head >= num && maxHeap.filter(_ == num).size > 0) remove(maxHeap)
      else remove(minHeap)
    }

    for((num, right) <- nums.zipWithIndex) {
      addToHeap(num)
      rebalanceHeaps(num)

      if(right - k  + 1 >= 0) {
        findMedian()

        val numToRemove = nums(right - k + 1)
        removeNum(numToRemove)
      }
    }
    ans.toArray
  }
}
