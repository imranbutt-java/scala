package com.scala.learn.interview.intervals

import scala.collection.mutable

/* imransarwar created on 12/12/2020*/
object MeetingRoom2 extends App {
  def minMeetingRooms(intervals: Array[Array[Int]]): Int = {
    if(intervals == null || intervals.length == 0) return 0
    val pq = mutable.PriorityQueue[Option[Int]](Some(0))(Ordering[Option[Int]].reverse)

    for {
      lastMeeting <- pq.dequeue
      booking <- intervals.sortBy(_(0))
    } {
      if(lastMeeting < booking(1)) pq.enqueue(Some(lastMeeting))
      pq.enqueue(Some(booking(1)))
    }
    pq.size
  }
}
