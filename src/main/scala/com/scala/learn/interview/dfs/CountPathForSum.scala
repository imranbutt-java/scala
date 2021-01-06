package com.scala.learn.interview.dfs

import scala.collection.mutable.ListBuffer

/* imransarwar created on 21/12/2020*/
/**
 * Given a binary tree and a number ‘S’, find all paths in the tree such that the sum of all
 * the node values of each path equals ‘S’. Please note that the paths can start or end at
 * any node but all paths must follow direction from parent to child (top to bottom).
 *
 *
 *           1
 *       7      5
 *           2     9
 */
object CountPathForSum extends App {
  case class TreeNode(_value: Int, _left: TreeNode = null, _right: TreeNode = null) {
    var value = _value
    var left = _left
    var right = _right
  }

  def countPathForSum(root: TreeNode, sum: Int): Int = {
    val ans = ListBuffer[Int]()

    def dfs(root: TreeNode, curPath: ListBuffer[Int]): Int = root match {
      case null => 0
      case node => {
        curPath.addOne(node.value)

        //Find all path sum till this node
        var pathCount = 0
        var pathSum = 0
        for(n <- curPath) {
          pathSum += n
          if(pathSum == sum) pathCount += 1
        }

        pathCount += dfs(node.left, curPath)
        pathCount += dfs(node.right, curPath)

        curPath.remove(curPath.size - 1)
        pathCount
      }
    }

    dfs(root, ans)
  }

  val root = TreeNode(1, TreeNode(7), TreeNode(5, TreeNode(2), TreeNode(9)))
  println(countPathForSum(root, 8))
}
