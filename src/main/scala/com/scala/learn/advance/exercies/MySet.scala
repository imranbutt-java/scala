package com.scala.learn.advance.exercies

import scala.annotation.tailrec

/* imransarwar created on 26/01/2020*/
/**
 * - Set instances are callable
 * set(2) //true means, does it contains 2
 * - Set Collections are Functions
 * trait Set[A] extends (A) => Boolean ...
 */
trait MySet[A] extends (A => Boolean) {

  /*
    EXERCISE - implement a functional set
   */
  def apply(elem: A): Boolean =
    contains(elem)

  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A] // union

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  /*
   EXERCISE #2
   - removing an element
   - intersection with another set
   - difference with another set
  */
  def -(elem: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A] // difference
  def &(anotherSet: MySet[A]): MySet[A]  // intersection

  // EXERCISE #3 - implement a unary_! = NEGATION of a set
  // set[1,2,3] =>
  def unary_! : MySet[A]
}

//MySet[A] is invariant, that's why we are not using Nothing as did in MyList
class EmptySet[A] extends MySet[A] {
  def contains(elem: A): Boolean = false
  def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this
  def foreach(f: A => Unit): Unit = ()

  // part 2
  def -(elem: A): MySet[A] = this
  def --(anotherSet: MySet[A]): MySet[A] = this
  def &(anotherSet: MySet[A]): MySet[A] = this

  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

// all elements of type A which satisfy a property
// { x in A | property(x) }
class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  def contains(elem: A): Boolean = property(elem)
  // { x in A | property(x) } + element = { x in A | property(x) || x == element }
  def +(elem: A): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || x == elem)

  // { x in A | property(x) } ++ set => { x in A | property(x) || set contains x }
  def ++(anotherSet: MySet[A]): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  // all integers => (_ % 3) => [0 1 2]
  // Means we can't give any definite answer, what are exact numbers which you may think, because,
  // for _ % 3 means the remainder [0 1 2] for numbers that may be any set of numbers
  def map[B](f: A => B): MySet[B] = politelyFail
  def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail
  def foreach(f: A => Unit): Unit = politelyFail

  def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && predicate(x))
  def -(elem: A): MySet[A] = filter(x => x != elem)
  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!")
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)

  def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new NonEmptySet[A](elem, this)

  /*
    tail ++ anotherSet works as a recursion
    [1 2 3] ++ [4 5] =
    [2 3] ++ [4 5] + 1 =
    [3] ++ [4 5] + 1 + 2 =
    [] ++ [4 5] + 1 + 2 + 3
    [4 5] + 1 + 2 + 3 = [4 5 1 2 3]
   */
  def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  /**
   * map is recursive call that keep on applying function f on each element till EmptySet and that returns
   * new EmptySet[B] so when we would apply + it would return new EmptySet[head, this]
   */
  def map[B](f: A => B): MySet[B] = (tail map f) + f(head)

  /**
   * Concatenating as ++ returns same provided MySet instead of using + that returns new EmptySet including elem
   */
  def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)
  def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

  // part 2
  // If elem is head, we would remove it straight forward, otherwise we would remove from tail and append head
  def -(elem: A): MySet[A] =
    if (head == elem) tail
    else tail - elem + head

  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  /*
  For intersection, we say both set have the same element, so filter says other set contains x too
  def &(anotherSet: MySet[A]): MySet[A] = filter(x => anotherSet.contains(x))
  \/
  means contains also available in set, so we may directly call using variable
  def &(anotherSet: MySet[A]): MySet[A] = filter(x => anotherSet(x))
  \/
  Further reducing would result as below
   */
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet) // intersection = filtering!

  // new operator
  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this.contains(x))
}

object MySet {
  /*
    val s = MySet(1,2,3) = buildSet(seq(1,2,3), [])
    = buildSet(seq(2,3), [] + 1)
    = buildSet(seq(3), [1] + 2)
    = buildSet(seq(), [1, 2] + 3)
    = [1,2,3]
   */
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object MySetPlayground extends App {
  println("Exercise 1")
  println("map...")
  val s = MySet(1,2,3,4)
  s + 5 ++ MySet(-1,-2) + 3 map(x => x * 10) foreach(x => print(x+", "))
  println("\nFlatmap...")
  s + 5 ++ MySet(-1,-2) + 3 flatMap (x => MySet(x * 10)) foreach(x => print(x+", "))
  println
  s + 5 ++ MySet(-1,-2) + 3 flatMap (x => MySet(x, x * 10)) foreach(x => print(x+", "))
  println("\nfilter...")
  s + 5 ++ MySet(-1,-2) + 3 flatMap (x => MySet(x, x * 10)) filter(_ % 2 == 0) foreach(x => print(x+", "))

  println("\nExercise 2")
  val negative = !s // s.unary_! = all the naturals not equal to 1,2,3,4
  println(negative(2))
  println(negative(5))

  val negativeEven = negative.filter(_ % 2 == 0)
  println(negativeEven(5))

  val negativeEven5 = negativeEven + 5 // all the even numbers > 4 + 5
  println(negativeEven5(5))
}



