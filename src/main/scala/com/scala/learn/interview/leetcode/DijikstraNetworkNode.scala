package com.scala.learn.interview.leetcode

/* imransarwar created on 21/08/2020*/
object DijikstraNetworkNode extends App {
  def networkDelayTime(times: Array[Array[Int]], N: Int, K: Int): Int = {
    val E = times.groupMap(t => t(0))(t => t(1) -> t(2))
    val dist = Array.fill(N+1)(Int.MaxValue)
    val pred = Array.ofDim[Int](N+1)
    var Q = collection.mutable.PriorityQueue[(Int, Int)]()(Ordering.by(_._2));
    dist(K) = 0;
    Q.enqueue(K -> 0);
    while (Q.nonEmpty){
      val (u,_) = Q.dequeue
      for {
        arr <- E.get(u)
        (v,w) <- arr
        if dist(u) + w < dist(v)
      } {
        dist(v) = dist(u) + w
        pred(v) = u
        Q += (v -> dist(v))
      }
    }
    val ans = dist.tail.max //element 0 is just placeholder to alight indexes with input
    pred.foreach(x => print(s"$x,"))
    if (ans == Int.MaxValue) -1 else ans;
  }
  println( networkDelayTime(Array(Array(2,1,1),
                                  Array(2,3,1),
                                  Array(3,4,1)), 4, 2) )
}
