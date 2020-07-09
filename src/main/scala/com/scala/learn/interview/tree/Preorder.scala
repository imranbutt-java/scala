package com.scala.learn.interview.tree

/* imransarwar created on 24/06/2020*/
object Preorder extends App {
  case class TreeNode(value: Int, left: TreeNode = null, right: TreeNode = null)

  def preorder(root: TreeNode): List[Int] = root match {
    case null => Nil
    case node => node.value :: preorder(node.left) ++ preorder(node.right)
  }

  val root = TreeNode(1, null, TreeNode(2, null, TreeNode(3)))
  println(preorder(root))
}
