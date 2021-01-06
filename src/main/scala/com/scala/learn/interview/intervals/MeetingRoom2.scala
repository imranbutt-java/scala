package com.scala.learn.interview.intervals

import scala.collection.mutable

/* imransarwar created on 12/12/2020*/
/**
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 *
 * Example 1:
 *
 * Input: [[0, 30],[5, 10],[15, 20]]
 * Output: 2
 */
object MeetingRoom2 extends App {
  def minMeetingRooms(intervals: Array[Array[Int]]): Int = {
    if(intervals == null || intervals.length == 0) return 0
    val minHeap = mutable.PriorityQueue[Option[Int]](Some(0))(Ordering.by(identity)).reverse
    val sortedByStart = intervals.sortBy(_.head)
    for {
      currentMeeting <- sortedByStart
      lastMeeting <- minHeap.head
    } {
      if (currentMeeting.head > lastMeeting) minHeap.dequeue
      minHeap.enqueue(Some(currentMeeting.last))
    }
    minHeap.size
  }
}
