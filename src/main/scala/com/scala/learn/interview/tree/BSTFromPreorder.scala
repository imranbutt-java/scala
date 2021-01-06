package com.scala.learn.interview.tree

/* imransarwar created on 05/01/2021*/
object BSTFromPreorder extends App {
  case class TreeNode(_x: Int, _left: TreeNode = null, _right: TreeNode = null) {
    var x = _x
    var left = _left
    var right = _right
  }
  def bstFromPreorder(preorder: Array[Int]): TreeNode = preorder match {
    case x if x.isEmpty => null
    case x => {
      val (left, right) = x.tail.span(_ < x.head)

      val root = TreeNode(x.head)
      root.left = bstFromPreorder(left)
      root.right = bstFromPreorder(right)
      root
    }
  }

  bstFromPreorder(Array(8,5,1,7,10,12))
}
