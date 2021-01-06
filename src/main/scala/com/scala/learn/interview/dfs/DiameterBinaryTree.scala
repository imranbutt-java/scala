package com.scala.learn.interview.dfs

/* imransarwar created on 21/12/2020*/
/**
 * Given a binary tree, find the length of its diameter.
 * The diameter of a tree may or may not pass through the root.
 *
 * Note: You can always assume that there are at least two leaf nodes in the given tree.
 */
class DiameterBinaryTree {
  case class TreeNode(_value: Int, _left: TreeNode, _right: TreeNode) {
    var value = _value
    var left = _left
    var right = _right
  }
  def diameterOfBinaryTree(root: TreeNode): Int = {
    var dia = 0
    def dfs(root: TreeNode): Int = root match {
      case null => 0
      case node => {
        //Hypothesis
        val left = dfs(node.left)
        val right = dfs(node.right)

        dia = Math.max(dia, left + right)
        1 + Math.max(left, right)
      }
    }
    dfs(root)
    dia
  }
}
