package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 07/09/2020*/
object ZigZagLevelOrderTraversal extends App {
  case class TreeNode(value: Int, left: TreeNode = null, right: TreeNode = null)

  def zigzagLevelOrder(root: TreeNode): List[List[Int]] = {
    val deque = mutable.ArrayDeque[TreeNode]()
    val ans = mutable.ListBuffer[List[Int]]()
    deque.addOne(root)

    while(!deque.isEmpty) {
      val level = deque.size
      val list = mutable.ListBuffer[Int]()
      for(i <- 0 until level) {
        val n = deque.removeHead(true)
        if(n != null && i % 2 == 0) list += n.value
        else if(n != null) list.insert(0, n.value)

        if(n != null) {
          deque.addOne(n.right)
          deque.addOne(n.left)
        }
      }
      ans += list.toList
    }
    ans.toList.filterNot(x => x.isEmpty).toList
  }

  val t7 = TreeNode(7)
  val t15 = TreeNode(15)
  val t20 = TreeNode(20, t15, t7)
  val t9 = TreeNode(9)
  val t3 = TreeNode(3, t9, t20)
  zigzagLevelOrder(t3).foreach(println)

}
