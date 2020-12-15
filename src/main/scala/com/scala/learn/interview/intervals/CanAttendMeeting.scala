package com.scala.learn.interview.intervals

/* imransarwar created on 12/12/2020*/
object CanAttendMeeting extends App {
  def canAttendMeetings(intervals: Array[Array[Int]]): Boolean = {
    intervals.sortBy(_(0)).foldLeft(-1){
      case (firstFinished, second) if(firstFinished > second(0)) => return false
      case (_, second) => second(1)
    }
    true
  }
}
