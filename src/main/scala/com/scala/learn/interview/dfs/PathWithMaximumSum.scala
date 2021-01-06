package com.scala.learn.interview.dfs

/* imransarwar created on 21/12/2020*/
object PathWithMaximumSum extends App {
  case class TreeNode(_value: Int, _left: TreeNode, _right: TreeNode) {
    var value = _value
    var left = _left
    var right = _right
  }

  def pathWithMaximumSum(root: TreeNode): Int = {
    var max = 0
    def dfs(root: TreeNode): Int = root match {
      case null => 0
      case node => {
        val left = dfs(node.left)
        val right = dfs(node.right)

        val iamMax = node.value + Math.max(0, left) + Math.max(0, right)

        max = Math.max(max, iamMax)

        node.value + Math.max(node.value, Math.max(left, right))
      }
    }
    dfs(root)
    max
  }
}
