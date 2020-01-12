package com.scala.learn.exercies

import scala.annotation.tailrec

/* Created by imransarwar on 2020-01-11 */
abstract class MyList[+A] {
  /*
  - head = first element of the list
   */
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  //protected def printElements: String
  def printElements: String
  //Polymorphic call
  override def toString: String = "["+printElements+"]"

  //Exercise
  //Type B, because transform would convert type A to B
  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]

  //Because of flatMap we have to add concatenation function
  def ++[B >: A](list: MyList[B]): MyList[B]
}

case object Empty extends MyList[Nothing] {
  // ??? means Nothing
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  //If printElements is protected and we call t.printElements it would be problem because, t is an external instance
  //and we can't printElements method from it.
  def printElements: String =
    if(t.isEmpty) ""+h
    else h + " " + t.printElements

  //Head is transformed and for tail recursive call called
  /*
  How recurssion working...
  [1,2,3].map(n * 2)
    => new Cons(2, [2,3].map(n * 2)
      => new Cons(2, newCons(4, [3].map(n * 2)))
        => new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
          => new Cons(2, new Cons(4, new Cons(6, Empty))))

   */
  def map[B](transformer: MyTransformer[A, B]): MyList[B] =
    new Cons(transformer.transorm(h), t.map(transformer))

  /*
  [1,2,3].flatMap(n => [n, n+1])
    => [1, 2] ++ [2, 3].flatMap(n => [n, n+1])
      => [1, 2] ++ [2, 3] ++ Empty.flatMap(n => [n+1])
        => [1,2] ++ [2,3] + Empty
          => [1,2,2,3]
   */
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
    transformer.transorm(h) ++ t.flatMap(transformer)

  //If head of the singly linked list passed with filter test method, we would recursively call tail and so on.
  /*
  HOw recursion working...
  [1,2,3].filter(n % 2 == 0)
  Here 1 would fail and we get the call t.filter(predicate) means
    => [2,3].filter(n % 2 == 0)
      => new Cons(2, [3].filter(n % 2 == 0))
        => new Cons(2, Empty.filter(n % 2 == 0)) //because, tail of [3] is empty
          => new Cons(2, Empty)
   */
  def filter(predicate: MyPredicate[A]): MyList[A] =
    if(predicate.test(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)

  /*
  [1,2] ++ [3, 4, 5]
    => new Cons(1, [2] ++ [3, 4, 5])
      => new Cons(1, new Cons(2, Empty ++ [3, 4, 5]))
        => new Cons(1, new Cons(2, [3, 4, 5])) means
        =  new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5, Empty))))
   */
  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons[B](h, t ++ list)
}

//Exercise
trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transorm(elem: A): B
}

object ListTest extends App {
  val listOfInt: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  val bListOfInt: MyList[Int] = new Cons[Int](4, new Cons[Int](5, Empty))
  val clonedListOfInt: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  val listOfStr: MyList[String] = new Cons[String]("Hi", new Cons[String]("Scala", Empty))

  println(listOfInt.toString)
  println(listOfStr.toString)

  println(listOfInt.map(new MyTransformer[Int, Int] {
    override def transorm(elem: Int): Int = elem * 2
  }))

  println(listOfInt.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element % 2 == 0
  }))

  println(listOfInt ++ bListOfInt)
  println(listOfInt.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transorm(elem: Int): MyList[Int] = new Cons(elem, new Cons[Int](elem + 1, Empty))
  }))

  println(clonedListOfInt == listOfInt)

}
