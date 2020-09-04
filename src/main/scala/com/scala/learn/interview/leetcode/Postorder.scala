package com.scala.learn.interview.leetcode

import scala.collection.mutable.ListBuffer

object Postorder extends App {
  case class TreeNode(value: Int, left: TreeNode = null, right: TreeNode = null)
  def postorderTraversal(root: TreeNode): List[Int] = root match {
    case null => Nil
    case root => postorderTraversal(root.left) ++ postorderTraversal(root.right)  ++ List(root.value)
  }

  /*
  LRV
      3
    /   \
  2       4
        /    \
     6        5
      Expected: 2,6,5,4,3
   */
  var dp = ListBuffer.fill(10)(false)

  dp.update(0, true)
  println(dp)
  val root = TreeNode(3, TreeNode(2), TreeNode(4, TreeNode(6), TreeNode(5)))
  println(postorderTraversal(root))
}














