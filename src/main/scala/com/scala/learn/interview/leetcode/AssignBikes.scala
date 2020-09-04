package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 23/08/2020*/
object AssignBikes extends App {
  def assignBikesPQ(workers: Array[Array[Int]], bikes: Array[Array[Int]]): Array[Int] = {
    val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by{t: (Int, Int, Int) => (t._1, t._2, t._3)}).reverse

    val n = workers.size
    val bikeOccupied = mutable.HashSet[Int]()

    val res = Array.fill(n)(-1)
    for((w, i) <- workers.zipWithIndex) {
      for((b, j) <- bikes.zipWithIndex) {
        val dist = Math.abs(w(0) - b(0)) + Math.abs(w(1) - b(1))
        pq.enqueue((dist, i, j))
      }
    }

    while(bikeOccupied.size < n && !pq.isEmpty) {
      val (dist, worker, bike) = pq.dequeue
      if(!bikeOccupied.contains(bike) && res(worker) == -1) {
        res(worker) = bike
        bikeOccupied += bike
      }
    }

    res
  }

  def assignBikes(workers: Array[Array[Int]], bikes: Array[Array[Int]]): Array[Int] = {
    // Using Bubble Sort
    val n = workers.size
    val m = bikes.size
    val buckets = Array.ofDim[mutable.ListBuffer[(Int, Int)]](2001)
    val assigned = Array.ofDim[Boolean](n)
    val occupied = Array.ofDim[Boolean](m)

    def distance(w: Array[Int], b: Array[Int]) = {
      Math.abs(w(0) - b(0)) + Math.abs(w(1) - b(1))
    }

    val res = Array.ofDim[Int](n)

    for(i <- 0 until n) {
      for(j <- 0 until m) {
        val dist = distance(workers(i), bikes(j))
        if(buckets(dist) == null) buckets(dist) = mutable.ListBuffer()
        buckets(dist).addOne((i, j))
      }
    }
    for(i <- 0 until 2001) {
      if(buckets(i) != null) {
        val size = buckets(i).size
        for(j <- 0 until size) {
          val w = buckets(i)(j)._1
          val b = buckets(i)(j)._2

          if(!assigned(w) && !occupied(b)) {
            res(w) = b
            assigned(w) = true
            occupied(b) = true
          }
        }
      }
    }
    res
  }

  assignBikesPQ(workers = Array(Array(0,0), Array(1,1), Array(2,0)), bikes = Array(Array(1,0), Array(2,2), Array(2,1))).foreach(println)
}
