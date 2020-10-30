package com.scala.learn.interview.array

/* imransarwar created on 09/09/2020*/
object StudentDoingHomeWorkInTime extends App {
  def busyStudent(startTime: Array[Int], endTime: Array[Int], queryTime: Int): Int = {
    var count = 0
    for {
      i <- startTime.indices
      if(endTime(i) - startTime(i) >= queryTime)
    } yield count += 1
    count
  }

  busyStudent(Array(1,2,3), Array(3,2,7), 4)
}
