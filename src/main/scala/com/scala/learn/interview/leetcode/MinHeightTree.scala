package com.scala.learn.interview.leetcode

/* imransarwar created on 19/08/2020*/
import scala.collection.mutable._

object MinHeightTree extends App {
  def findMinHeightTrees(n: Int, edges: Array[Array[Int]]): List[Int] = {
    // Remove leaves in each step
    // Create new leaves from remaining
    // At the end send remaining 1 or 2 leaves, which would be parent of MHTs
    if(n == 1) return List()
    val adjGraph = Map[Int, Set[Int]]()

    def buildGraph = {
      for(root <- 0 until n) adjGraph.put(root, Set[Int]())
      // Add bidirectional edges
      for(e <- edges) {
        adjGraph(e(0)).add(e(1))
        adjGraph(e(1)).add(e(0))
      }
    }

    buildGraph
    var leaves = adjGraph.filter(_._2.size == 1).map(_._1).toList
    var nodes = adjGraph.size

    while(nodes > 2) {
      val newLeaves = ListBuffer[Int]()
      nodes -= leaves.size

      for(leaf <- leaves) {
        val p: Int = adjGraph.get(leaf).get.head
        adjGraph.get(p).get.remove(leaf)
        if(adjGraph.get(p).get.size == 1)
          newLeaves.addAll(Set(p))
      }
      leaves = newLeaves.toList
    }
    leaves
  }

  println(findMinHeightTrees(4, Array(Array(1,0), Array(1,2), Array(1,3))))
}
