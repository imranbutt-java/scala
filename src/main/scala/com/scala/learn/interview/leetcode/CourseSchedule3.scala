package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 22/08/2020*/
object CourseSchedule3 extends App {
  def scheduleCourse(courses: Array[Array[Int]]): Int = {
    val sortedCourses = courses.sortBy(_(1))
    // That keeps the courses, we may complete and order them with available duration
    val maxHeap = mutable.PriorityQueue[Int]()

    // Time taken to complete the course
    var timeTaken = 0
    for(course <- sortedCourses) {
      timeTaken += course(0)
      maxHeap.enqueue(course(0))

      if(timeTaken > course(1)) timeTaken -= maxHeap.dequeue
    }
    maxHeap.size
  }

  println(scheduleCourse(Array( Array(2, 5), Array(2, 19), Array(1, 8), Array(1, 3) )))
//  println(scheduleCourse(Array( Array(100, 200), Array(200, 1300), Array(1000, 1250), Array(2000, 3200) )))
}
