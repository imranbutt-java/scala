package com.scala.learn.interview.tree

import scala.collection.immutable.HashSet

/* imransarwar created on 18/08/2020*/
object BSTTargetNum extends App {
  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
    }

  def findTarget(root: TreeNode, k: Int): Boolean = {
    def findTargetTR(r: TreeNode, set: Set[Int]): Boolean = {
      if(r == null) false
      else if(set.contains(r.value - k)) true
      else findTargetTR(r.left, set + (k - r.value)) || findTargetTR(r.right, set + (k - r.value))
    }
    findTargetTR(root, HashSet[Int](k - root.value))
  }

}
