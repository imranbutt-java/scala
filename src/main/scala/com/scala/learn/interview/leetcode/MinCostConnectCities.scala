package com.scala.learn.interview.leetcode

/* imransarwar created on 06/08/2020*/
object MinCostConnectCities extends App {
  def minimumCost(N: Int, connections: Array[Array[Int]]): Int = {
    val parent: Array[Int] = (0 until (N+1)).toArray
    var n = N

    def find(x: Int): Int = {
      if (parent(x) == x) parent(x)
      else {
        parent(x) = find(parent(x))
        parent(x)
      }
    }

    def union(x: Int, y: Int) = {
      val pX = find(x)
      val pY = find(y)

      if (pX != pY) {
        parent(pX) = pY
        n -= 1
      }
    }


    val sortedConnections = connections.sortWith(_(2) < _(2))
    sortedConnections.foreach(x => {
      x.foreach(print)
      println
    })

    parent.foreach(print)
    var res = 0

    for (c <- sortedConnections) {
      val x = c(0)
      val y = c(1)

      if (find(x) != find(y)) {
        res += c(2)
        union(x, y)
      }
    }

    if (n == 1) res else -1
  }

  println(minimumCost(3, Array(Array(1,2,5), Array(1,3,6), Array(2,3,1))))
}
