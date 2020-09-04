package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 01/09/2020*/
object FindModes extends App {
  def findMode(root: TreeNode): Array[Int] = {
    val map = mutable.Map[Int, Int]().withDefault(x => 0)
    def mode(root: TreeNode): Int = {
      if(root == null) 0
      else if(root != null && map.contains(root.value)) {
        map(root.value) = map(root.value) + 1
        1
      }
      else {
        map.put(root.value, 1)
        mode(root.left)
        mode(root.right)
      }
    }
    mode(root)
    map.filter(x => x._2 > 1).keys.toArray
  }

  class TreeNode(var _value: Int) {
    var value: Int = _value
    var left: TreeNode = null
    var right: TreeNode = null
  }

  val a =  new TreeNode(2)
  val b = new TreeNode(2)
  val r = new TreeNode(1)
  a.right = b
  r.right = a

  findMode(r)
}
