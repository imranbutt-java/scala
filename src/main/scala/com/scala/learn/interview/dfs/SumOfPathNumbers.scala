package com.scala.learn.interview.dfs

/* imransarwar created on 20/12/2020*/
/**
 * Given a binary tree where each node can only have a digit (0-9) value, each root-to-leaf path
 * will represent a number.
 * Find the total sum of all the numbers represented by all paths.
 *          1
 *      7      9
 *          2     9
 *
 *   17 + 192 + 199 = 408
 */
object SumOfPathNumbers extends App {
  case class TreeNode(_value: Int, _left: TreeNode = null, _right: TreeNode = null) {
    var value = _value
    var left = _left
    var right = _right
  }

  def leafNode(n: TreeNode): Boolean = n.left == null && n.right == null
  def sumOfPathNumbers(root: TreeNode): Int = {
    def helper(root: TreeNode, pathSum: Int): Int = root match {
      case null => 0
      case node if leafNode(node) => pathSum * 10 + node.value
      case node => {
        helper(node.left, pathSum * 10 + node.value) + helper(node.right, pathSum * 10 + node.value)
      }
    }

    helper(root, 0)
  }
  val root = TreeNode(1, TreeNode(7), TreeNode(9, TreeNode(2), TreeNode(9)))
  println(sumOfPathNumbers(root))
}
