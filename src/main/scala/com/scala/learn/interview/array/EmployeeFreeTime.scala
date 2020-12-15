package com.scala.learn.interview.array

/* imransarwar created on 07/12/2020*/
class EmployeeFreeTime {
  class Interval(var _start: Int, var _end: Int) {
    var start: Int = _start
    var end: Int = _end
  }

  def employeeFreeTime(schedule: List[List[Interval]]): List[Interval] = {
    // If empty return empty list
    if(schedule.isEmpty) return List[Interval]();

    // Sort and flatten the list so we would get single list with all intervals sorted
    val sorted = schedule.flatten.sortBy(_.start)

    // We have head, now take its end value as max value, that would be checked for all coming intervals
    sorted.tail.foldLeft((List[Interval](), sorted.head.end)) {(acc, next) => {
      next match {
        case x if acc._2 < x.start => (new Interval(acc._2, next.start) +: acc._1, Math.max(acc._2, next.end))
        case _ => (acc._1, Math.max(acc._2, next.end))
      }
    }}._1.sortBy(_.start)
  }
}
