package com.scala.learn.interview.twoheaps
import scala.collection.mutable._
/* imransarwar created on 25/12/2020*/
class MedianStream {

  val minHeap = PriorityQueue[Int]()(Ordering.by(-_))
  val maxHeap = PriorityQueue[Int]()(Ordering.by(identity))

  def addNum(num: Int) {

    if(maxHeap.isEmpty || maxHeap.head >= num) maxHeap.enqueue(num)
    else minHeap.enqueue(num)

    val minSize = minHeap.size
    val maxSize = maxHeap.size


    if(maxSize - minSize > 1) minHeap.enqueue(maxHeap.dequeue)
    else if(maxSize < minSize) maxHeap.enqueue(minHeap.dequeue)
  }

  def findMedian(): Double = {
    if(maxHeap.size == minHeap.size) (maxHeap.head + minHeap.head) / 2.0
    else maxHeap.head
  }
}
