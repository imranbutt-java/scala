package com.scala.learn.interview.leetcode

/* imransarwar created on 22/08/2020*/
object CourseSchedule2 extends App {
  def findOrder(numCourses: Int, prerequisites: Array[Array[Int]]): Array[Int] = {
    val ans = Array.ofDim[Int](numCourses)
    val visited = Array.fill(numCourses)(false)
    val graph = prerequisites.groupMap(c => c(1))(c => c(0) -> c(1))

    var i = numCourses - 1
    def dfs(course: Int) {
      visited(course) = true
      val edges = graph.getOrElse(course, Array())
      for(edge <- edges) {
        if(!visited(edge._1)) dfs(edge._1)
      }
      ans(i) += course
      i -= 1
    }

    for(course <- (0 until numCourses)) {
      if(!visited(course)) {
        dfs(course)
      }
    }
    ans
  }

  findOrder(2, Array(Array(1,0))).foreach(println)
}
