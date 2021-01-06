package com.scala.learn.interview.dfs

import scala.collection.mutable.ListBuffer

/* imransarwar created on 20/12/2020*/
object AllPathForSum extends App {
  case class TreeNode(_value: Int, _left: TreeNode, _right: TreeNode) {
    var value = _value
    var left = _left
    var right = _right
  }
  def allPathForSum(root: TreeNode, sum: Int): List[List[Int]] = {
    val ans = ListBuffer[List[Int]]()
    def leafNode(n: TreeNode): Boolean = n.left == null && n.right == null
    def pathSum(root: TreeNode, sum: Int, sol: ListBuffer[Int]): Unit = {
      if(root != null) {
        sol.addOne(root.value)

        if(root.value == sum && leafNode(root)) ans.addOne(sol.toList)
        else {
          pathSum(root.left, sum - root.value, sol)
          pathSum(root.right, sum - root.value, sol)
        }
        sol.remove(sol.size - 1)
      }
    }
    pathSum(root, sum, ListBuffer[Int]())
    ans.toList
  }
}
