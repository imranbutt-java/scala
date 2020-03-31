package com.scala.learn.interview.Lists

import scala.annotation.tailrec

// Could be made trait too !!!
//trait RList[+T] {
sealed abstract class RList[+T] {
  def head: T
  def tail: RList[T]
  def isEmpty: Boolean
//   def headOption: Option[T]
  //The below function won't compile, bcz covariant is in Contravariant position, solution is with Subtype
//  def prepend(elem: T): RList[T]
  // By making prepend as :: we made it right associative
  def ::[S >: T](elem: S): RList[S] = new ::(elem, this)

  // Q: Find k-th element at index
  def apply(index: Int): T
}

case object RNil extends RList[Nothing] {
  // Note Throwing exception is a side effect with pure FP
  // For the sake of safety you may use Option as commented
  override def head: Nothing = throw new NoSuchElementException
  override def tail: RList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def toString: String = "[]"
//   override def headOption: Option[Nothing] = None
  // As prepend implementation is same, so prepend is defined in parent class
//  override def prepend[S >: Nothing](elem: S): RList[S] = Cons(elem, this)

  override def apply(index: Int): Nothing = throw new IndexOutOfBoundsException
}

// Cons renamed to :: as acceptable name class in scala
case class ::[+T](override val head: T, override val tail: RList[T]) extends RList[T] {
  override def isEmpty: Boolean = false

  override def toString: String = {
    @tailrec
    def toStringTailRec(remaining: RList[T], result: String): String = {
      if(remaining.isEmpty) result
      else if(remaining.tail.isEmpty) s"$result${remaining.head}"
      else toStringTailRec(remaining.tail, s"$result${remaining.head}, ")
    }
    "["+ toStringTailRec(this, "") +"]"
  }

//  override def prepend[S >: T](elem: S): RList[S] = Cons(elem, this)

  override def apply(index: Int): T = {
    if(index < 0) throw new NoSuchElementException
    @tailrec
    def applyTailRec(remaining: RList[T], currentIndex: Int): T = {
      if(currentIndex == index) remaining.head
      else applyTailRec(remaining.tail, currentIndex + 1)
    }
    applyTailRec(this, 0)
  }
}

object ListProblems extends App {

  RNil.:: (2) == 2 :: RNil // It gives good Syntactical Sugar

//  val aList = Cons(1, Cons(2, Cons(3, RNil)))
//  val aList = ::(1, ::(2, ::(3, RNil)))
  val aList = 1 :: 2 :: 3 :: RNil // RNil.::(3).::(2).::(1)
  println(aList)
  println(aList(2))
}
