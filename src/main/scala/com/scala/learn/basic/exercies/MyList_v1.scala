//package com.scala.learn.basic.exercies
//
//import scala.annotation.tailrec
//
///* Created by imransarwar on 2020-01-11 */
//abstract class MyList {
//  /*
//  - head = first element of the list
//   */
//  def head: Int
//  def tail: MyList
//  def isEmpty: Boolean
//  def add(element: Int): MyList
//  //protected def printElements: String
//  def printElements: String
//  //Polymorphic call
//  override def toString: String = "["+printElements+"]"
//}
//
//object Empty extends MyList {
//  // ??? means Nothing
//  def head: Int = throw new NoSuchElementException
//  def tail: MyList = throw new NoSuchElementException
//  def isEmpty: Boolean = true
//  def add(element: Int): MyList = new Cons(element, Empty)
//  def printElements: String = ""
//}
//
//class Cons(h: Int, t: MyList) extends MyList {
//  def head: Int = h
//  def tail: MyList = t
//  def isEmpty: Boolean = false
//  def add(element: Int): MyList = new Cons(element, this)
//
//  //If printElements is protected and we call t.printElements it would be problem because, t is an external instance
//  //and we can't printElements method from it.
//  def printElements: String =
//    if(t.isEmpty) ""+h
//    else h + " " + t.printElements
//}
//
//object ListTest extends App {
//  val list = new Cons(1, new Cons(2, new Cons(3, Empty)))
//  println(list.add(4).head)
//  //Note list is immutable so the below list would remain same
//  println(s"Head: ${list.head} and Tail: ${list.tail.tail.head}")
//
//  println(list.toString)
//
//}

abstract class MyList_v1 {}
