package com.scala.learn.interview.leetcode

/* imransarwar created on 21/08/2020*/
import scala.collection.mutable

object CourseSchedule extends App {
  def canFinish(numCourses: Int, prerequisites: Array[Array[Int]]): Boolean = {
    val indegree = Array.ofDim[Int](numCourses)
    var completedCourse = 0

    // Course depend on other no of courses
    prerequisites.foreach(x => indegree(x(0)) += 1)
    println("# Indegree")
    indegree.foreach(println)

    val stack = mutable.Stack[Int]()
    for((course, ind) <- indegree.zipWithIndex)
      if(course == 0) stack.push(ind)
    println("# stack")
    stack.foreach(println)


    while(!stack.isEmpty) {
      val studiedCourse = stack.pop
      completedCourse += 1

      for(next <- prerequisites) {
        if(next(1) == studiedCourse) {
          indegree(next(0)) -= 1
          if(indegree(next(0)) == 0) stack.push(next(0))
        }
      }
    }
    completedCourse == numCourses
  }
  println(canFinish(3, Array(Array(0,1), Array(1,2))))
}
