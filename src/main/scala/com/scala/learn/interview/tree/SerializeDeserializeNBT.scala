package com.scala.learn.interview.tree

import scala.collection.mutable.Queue

/* imransarwar created on 05/01/2021*/
object SerializeDeserializeNBT extends App {
  case class Node(var _value: Int) {
    var value: Int = _value
    var children: List[Node] = List()
  }
  // Encodes a tree to a single string.
  final val separator = ","
  def serialize(root: Node): String = root match {
    case null => "x,"
    case x => {
      root.value + separator + root.children.size + separator + (for {
        child <- root.children
      } yield serialize(child)).reduce(_+_)
    }
  }

  // Decodes your encoded data to tree.
  def deserialize(data: String): Node = {
    def dfs(q: Queue[String]): Node = {
      val value = q.dequeue
      if(value == "x") return null

        val head = value.split(":")
        val rootVal = head(0)
        val noOfChildren = head(1)
        val root = new Node(rootVal.toInt)
        for(i <- 0 until noOfChildren.toInt) {
          val child = dfs(q)
          if(child != null)
            root.children =  root.children :+ child
        }
        root

    }
    dfs(Queue[String]().enqueueAll(data.split(",")))
  }

  val root = Node(1)
  val n1 = Node(3)
  n1.children = n1.children :+ Node(5)
  n1.children = n1.children :+ Node(6)
  root.children = root.children :+ n1
  root.children = root.children :+ Node(2)
  root.children = root.children :+ Node(4)

  val ser = serialize(root)
  println(ser)
  val der = deserialize(ser)
  println(der)
}
