package com.scala.learn.interview.dfs

/* imransarwar created on 20/12/2020*/
/**
 * Given a binary tree and a number ‘S’, find if the tree has a path from root-to-leaf
 * such that the sum of all the node values of that path equals ‘S’.

 */
object BinaryRootToLeafSum extends App {
  case class TreeNode(_value: Int, _left: TreeNode, _right: TreeNode) {
    var value = _value
    var left = _left
    var right = _right
  }
  def leafNode(node: TreeNode): Boolean = node.left == null && node.right == null
  def binaryRootToLeafSum(root: TreeNode, sum: Int): Boolean = root match {
    case null => false
    case n if sum == n.value && leafNode(n) => true
    case n => binaryRootToLeafSum(n.left, sum - n.value) || binaryRootToLeafSum(n.right, sum - n.value)
  }
}
