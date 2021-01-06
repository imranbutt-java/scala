package com.scala.learn.interview.tree

import scala.collection.mutable.Queue

/* imransarwar created on 05/01/2021*/
object SerializeDeserialzeBT extends App {
  case class TreeNode(_x: Int, _left: TreeNode = null, _right: TreeNode = null) {
    var value = _x
    var left = _left
    var right = _right
  }
  // Encodes a list of strings to a single string.
  def serialize(root: TreeNode): String = root match {
    case null => "x,"
    case root => root.value +","+ serialize(root.left) + serialize(root.right)
  }

  // Decodes a single string to a list of strings.
  def deserialize(data: String): TreeNode = {
    def dfs(q: Queue[String]): TreeNode = {
      val value = q.dequeue
      if(value == "x") return null
      val root = TreeNode(value.toInt)
      root.left = dfs(q)
      root.right = dfs(q)
      root
    }
    dfs(Queue[String]().enqueueAll(data.split(",")))
  }
  val root = TreeNode(2, TreeNode(1), TreeNode(3))
  val ser = serialize(root)
  println(ser)
  val des = deserialize(ser)
  println(des)
}
