package com.scala.learn.interview.leetcode

/* imransarwar created on 24/06/2020*/
object Inorder extends App {
  case class TreeNode(value: Int, left: TreeNode = null, right: TreeNode = null)

  def inorder(root: TreeNode): List[Int] = root match {
    case null => Nil
    case node => inorder(node.left) ::: List(node.value) ::: inorder(node.right)
  }

  /*
  LVR
      3
    /   \
  2       4
    \
      5
      Expected: 2, 5, 3, 4
   */
  val root = TreeNode(3, TreeNode(2, right = TreeNode(5) ), TreeNode(4))
  println(inorder(root))
}
