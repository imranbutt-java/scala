package com.scala.learn.interview.design

import scala.collection.mutable.ListBuffer
import scala.util.Random
import scala.util.control.Breaks._

/* imransarwar created on 06/12/2020*/
class SkipList {
  case class Node(value: Int, _left: Node = null,
                  _right: Node = null,
                  _up: Node = null,
                  _down: Node = null) {
    var left = _left
    var right = _right
    var up = _up
    var down = _down
  }

  final val DEFAULT_PROB: Double = 0.5
  final val random: Random = new Random()
  final val sentinels = ListBuffer[Node]()
  sentinels.addOne(Node(Int.MinValue))

  // search
  def search(target: Int): Boolean = {
    val smallerOrEquals = getSmallerOrEqual(target)
    smallerOrEquals.value == target
  }
  // add
  def add(num: Int): Unit = {
    val cur = getSmallerOrEqual(num)
    val toInsert = Node(num)
    append(cur, toInsert)
    populateLevelUp(toInsert)
  }
  // populateLevelUp
  def populateLevelUp(toInsert: Node): Unit = {
    var curLeft = toInsert.left
    var cur = toInsert

    while(flipCoin()) {
      // On current level go back till I find last node and add new Layer or if we are in some below layer
      // find node on same layer which is connected with upper layer
      while(curLeft.left != null && curLeft.up == null) {
        curLeft = curLeft.left
      }
      // Adding new Layer on Top
      if(curLeft.up == null) {
        val newSenital = Node(Int.MinValue)
        curLeft.up = newSenital
        newSenital.down = curLeft
        sentinels.addOne(newSenital)
      }
      // Move to upper Layer
      curLeft = curLeft.up
      // Create New Node to be inserted
      val newNode = Node(toInsert.value)
      cur.up = newNode
      newNode.down = cur
      cur = cur.up
      curLeft.right = cur
      cur.left = curLeft
    }
  }
  // append
  def append(prev: Node, cur: Node): Unit = {
    val next = prev.right
    prev.right = cur
    cur.left = prev

    if(next != null) {
      next.left = cur
      cur.right = next
    }
  }
  // erase
  def erase(num: Int): Boolean = {
    val toRemove = getSmallerOrEqual(num)
    if(toRemove.value != num) return false

    var cur = toRemove
    while(cur != null) {
      val prev = cur.left
      val next = cur.right
      prev.right = next
      if(next != null) next.left = prev
      cur = cur.up
    }
    true
  }
  // getSmallerOrEqual
  def getSmallerOrEqual(target: Int): Node = {
    var cur = sentinels(sentinels.size - 1)
    breakable {
      while (cur != null) {
        if (cur.right == null || cur.right.value > target) {
          if (cur.down == null) break
          cur = cur.down
        } else cur = cur.right
      }
    }
    cur
  }
  // flipCoin
  def flipCoin(): Boolean = {
    random.nextDouble() < DEFAULT_PROB
  }

  // toString
  override def toString: String = {
    var node = sentinels(0)
    val sb = new StringBuilder()
    var i = 0
    while(node != null) {
      var itr = node
      while(itr != null) {
        if(itr.value == Int.MinValue) {
          sb.append(s"L${i}").append(" --> ")
          i += 1
        } else
          sb.append(itr.value).append(" --> ")
        itr = itr.right
      }
      sb.append("\n")
      node = node.up
    }
    sb.toString
  }
}
object SkipList extends App {
  var obj = new SkipList()
  obj.add(1)
  obj.add(2)
  obj.add(3)
  obj.search(2)
  println(obj)
}
