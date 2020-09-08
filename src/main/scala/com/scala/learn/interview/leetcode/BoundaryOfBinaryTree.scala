package com.scala.learn.interview.leetcode

/* imransarwar created on 07/09/2020*/
object BoundaryOfBinaryTree extends App {
  case class TreeNode(value: Int, left: TreeNode = null, right: TreeNode = null)
  def boundaryOfBinaryTree(root: TreeNode): List[Int] = {
    def leafNodes(r: TreeNode, acc: List[Int]): List[Int] = r match {
      case null => Nil
      case node if node.left != null => leafNodes(node.left, acc)
      case node if node.left == null && node.right == null => acc ::: List(node.value)
      case node if node.right != null => leafNodes(node.right, acc)
    }

    def leftNodes(r: TreeNode, acc: List[Int]): List[Int] = r match {
      case null => Nil
      case node if node.left != null => List(node.value) ::: leftNodes(node.left, acc)
      case node if node.right != null => List(node.value) ::: leftNodes(node.right, acc)
      case _ => acc
    }

    def rightNodes(r: TreeNode, acc: List[Int]): List[Int] = r match {
      case null => Nil
      case node if node.right != null => List(node.value) ::: leftNodes(node.right, acc)
      case node if node.left != null => List(node.value) ::: leftNodes(node.left, acc)
      case _ => acc
    }

    if(root == null) return List()
    leftNodes(root.left, List.empty) ::: leafNodes(root.left, List.empty) ::: leafNodes(root.right, List.empty) ::: rightNodes(root.right, List.empty)
  }

  val t4 = TreeNode(4)
  val t3 = TreeNode(3)
  val t2 = TreeNode(2, t3, t4)
  val t1 = TreeNode(1, null, t2)
  boundaryOfBinaryTree(t1).foreach(println)
}
