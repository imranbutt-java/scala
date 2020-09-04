package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 20/08/2020*/
object CloneGraph extends App {
  class Node(var _value: Int) {
    var value: Int = _value
    var neighbors: List[Node] = List()
  }

  def cloneGraph(graph: Node): Node = {
    val map = mutable.Map[Node, Node]()

    def bfsClone(p: Node): Node = {
      if(map.contains(p)) map(p)
      else {
        val n = new Node(p.value)
        map  += (p -> n)
        println(n.value)

        for(c <- p.neighbors) {
          n.neighbors = bfsClone(c) :: n.neighbors
        }
        n
      }
    }
    bfsClone(graph)
  }

  val p = new Node(1)
  val n = new Node(2)
  p.neighbors = n :: List()
  n.neighbors = p :: List()
  cloneGraph(p).neighbors.foreach(x => println(x.value))
}
