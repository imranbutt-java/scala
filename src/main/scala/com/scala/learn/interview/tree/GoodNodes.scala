package com.scala.learn.interview.tree

/* imransarwar created on 06/12/2020*/
object GoodNodes extends App {
  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
  }
  def goodNodes(root: TreeNode): Int = {
    def dfs(root: TreeNode, max: Int): Int = root match {
      case null => 0
      case r if r.value >= max => {
        1 + dfs(r.left, Math.max(r.value, max)) + dfs(r.right, Math.max(r.value, max))
      }
    }
    dfs(root, Int.MinValue)
  }
}

