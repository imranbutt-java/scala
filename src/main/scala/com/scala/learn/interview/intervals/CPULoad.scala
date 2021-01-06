package com.scala.learn.interview.intervals

import scala.collection.mutable
import scala.util.Sorting

/* imransarwar created on 25/12/2020*/
/**
 * Maximum CPU Load (hard) #
 * We are given a list of Jobs. Each job has a Start time, an End time, and a CPU load when it is running.
 * Our goal is to find the maximum CPU load at any time if all the jobs are running on the same machine.
 *
 * Example 1:
 *
 * Jobs: [[1,4,3], [2,5,4], [7,9,6]]
 * Output: 7
 * Explanation: Since [1,4,3] and [2,5,4] overlap, their maximum CPU load (3+4=7) will be when both the
 * jobs are running at the same time i.e., during the time interval (2,4).
 */
object CPULoad extends App {
  case class Job(_start: Int, _end: Int, _load: Int) {
    var start = _start
    var end = _end
    var load = _load
  }

  def maxCPULoad(jobs: List[Job]): Int = {
    val sortedJob = jobs.sortBy(_._end)
    val minHeap = mutable.PriorityQueue[Job]()(Ordering.by(_._end))

    var maxLoad = 0
    var currentLoad = 0
    for(job <- sortedJob) {
      while(minHeap.nonEmpty && job.start > minHeap.head.`end`)
        currentLoad -= minHeap.dequeue.`end`
      minHeap.enqueue(job)
      currentLoad += job.load
      maxLoad = Math.max(maxLoad, currentLoad)
    }
    maxLoad
  }

  println(maxCPULoad(List(Job(1, 4, 3), Job(2, 5, 4), Job(7, 9, 6))))
}
