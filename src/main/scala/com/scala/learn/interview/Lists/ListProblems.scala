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

  // Q: Find List Length
  def length: Int

  // Q: Reverse the List
  def reverse: RList[T]

  // Q: Concatenate list
  def ++[S >: T](otherList: RList[S]): RList[S]

  // Q: Remove and element
  def removeAt(index: Int): RList[T]

  // Q: Implement Map, FlatMap and Filter
  def map[S](f: T => S): RList[S]
  def flatMap[S](f: T => RList[S]): RList[S]
  def filter(f: T => Boolean): RList[T]
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

  override def length: Int = 0

  override def reverse: RList[Nothing] = RNil

  override def ++[S >: Nothing](otherList: RList[S]): RList[S] = otherList

  override def removeAt(index: Int): RList[Nothing] = throw new NoSuchElementException

  override def map[S](f: Nothing => S): RList[S] = RNil
  override def flatMap[S](f: Nothing => RList[S]): RList[S] = RNil
  override def filter(f: Nothing => Boolean): RList[Nothing] = RNil
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

  override def length: Int = {
    @tailrec
    def lengthTailRec(remaining: RList[T], count: Int): Int = {
      if(remaining.isEmpty) count + 1
      else lengthTailRec(remaining.tail, count+1)
    }
    lengthTailRec(this, 0)
  }

  // We tried to create new list, concatenating with head
  override def reverse: RList[T] = {
    @tailrec
    def reverseTailRec(remaining: RList[T], result: RList[T]): RList[T] = {
      if(remaining.isEmpty) result
      else reverseTailRec(remaining.tail, remaining.head :: result)
    }
    reverseTailRec(this, RNil)
  }

  /**
   * It appends 2 RList
   * Complexity: O(M) + O(M + N)
   * N = Length of this List
   * M = Length of other List
   * @param otherList
   * @tparam S
   * @return
   */
  override def ++[S >: T](otherList: RList[S]): RList[S] = {

    // We may also give solution, but it would stack recursive, bcz ++ is evaluated first before prepend
    // head :: (tail ++ otherList)
    /*
    [1,2,3] ++ [4,5] = concatListTailRec([4,5], [3,2,1])
    = concatListTailRec([5], [4,3,2,1])
    = concatListTailRec([], [5,4,3,2,1])
    = [5,4,3,2,1]
    [5,4,3,2,1].reverse = [1,2,3,4,5]

    Complexity = O(N)
     */
    @tailrec
    def concatListTailRec(remaining: RList[S], acc: RList[S]): RList[S] = {
      if(remaining.isEmpty) acc
      else concatListTailRec(remaining.tail, remaining.head :: acc)
    }
    concatListTailRec(otherList, this.reverse).reverse
  }

  /*
  we may also implement below solution but it is stack recursive, bcz it evaluates this part tail.removeAt(index - 1)
  first and then prepend
  override def removeAt(index: Int): RList[T] = {
    if(index == 0) tail
    else head :: tail.removeAt(index - 1)
  }
  [1,2,3,5].removeAt(2) = removeAtTailRec([1,2,3,4,5], 0, [])
  = removeAtTailRec([2,3,4,5], 0, [1])
  = removeAtTailRec([3,4,5], 1, [2,1])
  = removeAtTailRec([4,5], 2, [2,1])
  = [2,1].reverse ++ [4,5]
  = [1,2] ++ [4,5] = [1,2,4,5]

  Complexity = O(N)
   */
  override def removeAt(index: Int): RList[T] = {
    if(index > this.length || index < 0) throw new IndexOutOfBoundsException

    @tailrec
    def removeAtTailRec(remaining: RList[T], newList: RList[T], currentInd: Int): RList[T] = {
      if(currentInd == index) newList.reverse ++ remaining.tail
      else removeAtTailRec(remaining.tail, remaining.head :: newList, currentInd + 1)
    }
    removeAtTailRec(this, RNil, 0)
  }

  /*
  The below implementation easy and pretty but it is stack recursive
  override def map[S](f: T => S): RList[S] = f(head) :: tail.map(f)

  [1,2,3].map(x => x + 1) = mapTailRec([1,2,3], [])
  = mapTailRec([2,3], [f(1)]) => mapTailRec([2,3], [2])
  = mapTailRec([3], [f(2), 2]) => mapTailRec([3], [3, 2])
  = mapTailRec([], [f(3), 3, 2]) => mapTailRec(4, 3, 2)
  = RList(4,3,2).reverse
  = RList(2,3,4)

  Complexity = O(N + O(f))
   */
  override def map[S](f: T => S): RList[S] = {
    @tailrec
    def mapTailRec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if(remaining.isEmpty) acc.reverse
      else mapTailRec(remaining.tail, f(remaining.head) :: acc)
    }
    mapTailRec(this, RNil)
  }

  /*
  Below is a good solution but would cause stack recursion
  f(head) ++ tail.flatMap(f)
  [1,2,3].flatMap(x => [x, x+1]) = flatMapTailRec([1,2,3], [])
  flatMapTailRec([2,3], f([1])) => flatMapTailRec([2,3], [1,2])
  flatMapTailRec([3], f(2) ++ [1,2]) => flatMapTailRec([3], [2,3] ++ [1,2])
  flatMapTailRec([], f(3) ++ [2,3] ++ [1,2]) => flatMapTailRec([], [3,4] ++ [2,3] ++ [1,2])
  [3,4,2,3,1,2].reverse => [2,1,3,2,4,3]
  But we need [1,2,2,3,3,4]

  To fix that we would reverse on f(remaining.head).reverse
  Complexity:
  O(sum of all the lengths of f(RList) = Z)
  implies that O(Z^2)

   */
  override def flatMap[S](f: T => RList[S]): RList[S] = {
    @tailrec
    def flatMapTailRec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if(remaining.isEmpty) acc.reverse
      else flatMapTailRec(remaining.tail, f(remaining.head).reverse ++ acc)
    }
    flatMapTailRec(this, RNil)
  }

  // Complexity: O(N)
  override def filter(f: T => Boolean): RList[T] = {
    @tailrec
    def filterTailRec(remaining: RList[T], acc: RList[T]): RList[T] = {
      if(remaining.isEmpty) acc.reverse
      else if(f(remaining.head)) filterTailRec(remaining.tail, remaining.head :: acc)
      else filterTailRec(remaining.tail, acc)
    }
    filterTailRec(this, RNil)
  }
}

// Creating companion object, to create RList
object RList {
  def from[T](iterable: Iterable[T]): RList[T] = {
    @tailrec
    def convertToRListTailRec(remaining: Iterable[T], acc: RList[T]): RList[T] = {
      if(remaining.isEmpty) acc
      else convertToRListTailRec(remaining.tail, remaining.head :: acc)
    }
    convertToRListTailRec(iterable, RNil).reverse
  }
}

object ListProblems extends App {

  RNil.:: (2) == 2 :: RNil // It gives good Syntactical Sugar

//  val aList = Cons(1, Cons(2, Cons(3, RNil)))
//  val aList = ::(1, ::(2, ::(3, RNil)))
  val aList = 1 :: 2 :: 3 :: RNil // RNil.::(3).::(2).::(1)
  println("# Find K-th Element")
  println(aList)
  println(aList(2))

  println("# Find Length of List")
  println(aList.length)

  println("# Reverse")
  println(aList.reverse)

  println("# Range")
  println(RList.from(1 to 10))

  println("# Concatenate")
  println(aList ++ (4 :: 5 :: RNil))

  println("# Remove At")
  println(RList.from(1 to 5).removeAt(2))

  println("# Map Function")
  println(RList.from(1 to 5).map(x => x + 1))

  println("# FlatMap Function")
  val startAt = System.currentTimeMillis()
  println(RList.from(1 to 10000).flatMap(x => x :: (x + 1) :: RNil))
  println(s"Time Spent: ${System.currentTimeMillis() - startAt}")

  println("# filter Function")
  println(RList.from(1 to 5).filter(x => x % 2 == 0))
}
