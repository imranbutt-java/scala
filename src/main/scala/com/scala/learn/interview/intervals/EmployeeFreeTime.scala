package com.scala.learn.interview.intervals

import scala.collection.mutable._

/* imransarwar created on 25/12/2020*/
/**
 * Employee Free Time (hard) #
 * For ‘K’ employees, we are given a list of intervals representing the working hours of each employee.
 * Our goal is to find out if there is a free interval that is common to all employees. You can assume
 * that each list of employee working hours is sorted on the start time.
 *
 * Example 1:
 *
 * Input: Employee Working Hours=[[[1,3], [5,6]], [[2,3], [6,8]]]
 * Output: [3,5]
 * Explanation: Both the employees are free between [3,5].
 */
object EmployeeFreeTime extends App {
  def freeTime(hours: Array[Array[Int]]): Array[Array[Int]] = {
    val ans = ListBuffer[Array[Int]]()

    val minHeap = PriorityQueue[Array[Int]]()(Ordering.by(_.head)).reverse
    hours.foreach(minHeap.enqueue(_))

    var lastEnded = -1
    while(minHeap.nonEmpty) {
      val current = minHeap.dequeue
      if(lastEnded != -1 && current.head > lastEnded) ans.addOne(Array(lastEnded, current.head))
      lastEnded = Math.max(lastEnded, current.last)
    }
    ans.toArray
  }
}
