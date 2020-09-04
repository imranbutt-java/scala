package com.scala.learn.interview.tree

import sun.security.ec.point.ProjectivePoint.Mutable

import scala.collection.mutable._

/* imransarwar created on 25/07/2020*/
object CloneNArrayTree extends App {

  class Node(var _value: Int) {
    var value: Int = _value
    var children: List[Node] = List()
  }

  def cloneTree(root: Node): Node = {
    if(root == null) null
    var copied: Node = new Node(root.value)
    copied.children.appended(List(new Node(5), new Node(2)))

    List(new Node(5), new Node(2)) ++ copied.children
    copied.children.foreach(print)
    copied
  }

  val n = cloneTree(new Node(1))
  println(n.value)
  n.children.foreach(print)
  val l = ListBuffer[Int]()
  l.append(1).append(2)

  println(l)
}
