package com.scala.learn.basic.exercies

import com.scala.learn.basic.lectures.part2oop.Generics.MyList

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
  //MyPredicate is changed with Function Types
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]

  //Because of flatMap we have to add concatenation function
  def ++[B >: A](list: MyList[B]): MyList[B]

  //hofs
  def foreach(f: A => Unit): Unit
  def sort(compare:(A, A) => Int): MyList[A]
  def zipWith[B,C](list: MyList[B], zip: (A,B) => C): MyList[C]
  def fold[B](start: B)(operator: (B, A) => B): B
}

case object Empty extends MyList[Nothing] {
  // ??? means Nothing
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList[B] = Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  def foreach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty
  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if(!list.isEmpty) throw new RuntimeException("Lists are not equal.")
    else Empty
  def fold[B](start: B)(operator: (B, Nothing) => B): B = start
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
  // transformer.transorm(h) changed bcz of Function Types
  def map[B](transformer: A => B): MyList[B] =
    new Cons(transformer(h), t.map(transformer))

  /*
  [1,2,3].flatMap(n => [n, n+1])
    => [1, 2] ++ [2, 3].flatMap(n => [n, n+1])
      => [1, 2] ++ [2, 3] ++ Empty.flatMap(n => [n+1])
        => [1,2] ++ [2,3] + Empty
          => [1,2,2,3]
   */
  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)

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
  def filter(predicate: A => Boolean): MyList[A] =
    if(predicate(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)

  /*
  [1,2] ++ [3, 4, 5]
    => new Cons(1, [2] ++ [3, 4, 5])
      => new Cons(1, new Cons(2, Empty ++ [3, 4, 5]))
        => new Cons(1, new Cons(2, [3, 4, 5])) means
        =  new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5, Empty))))
   */
  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons[B](h, t ++ list)

  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  def sort(compare: (A, A) => Int): MyList[A] = {
    //Auxilary Function, it is not tail rec implementation
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if(sortedList.isEmpty) new Cons(x, Empty)
      else if(compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
      else new Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedList = t.sort(compare)
    insert(h, sortedList)
  }

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
    if(list.isEmpty) throw new RuntimeException("Lists are not equal.")
    else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))
  }

  /*
  [1,2,3].fold(0)(+) =        // Here operator(1+0)
    [2,3].fold(1)(+) =
      [3].fold(3)(+) =
        [].fold(6)(+) =       // Here Empty returns start
          6
   */
  def fold[B](start: B)(operator: (B, A) => B): B =
    t.fold(operator(start, h))(operator)
}

//Exercise
//trait MyPredicate[-T] {
//  def test(element: T): Boolean
//}
//
//trait MyTransformer[-A, B] {
//  def transorm(elem: A): B
//}

object ListTest extends App {
  val listOfInt: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  val bListOfInt: MyList[Int] = new Cons[Int](4, new Cons[Int](5, Empty))
  val clonedListOfInt: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  val listOfStr: MyList[String] = new Cons[String]("Hi", new Cons[String]("Scala", Empty))

  println(listOfInt.toString)
  println(listOfStr.toString)

  /*
  CHANGED THIS
  println(listOfInt.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }))
  TO
  println(listOfInt.map(elem => elem * 2))
  THEN
  println(listOfInt.map(_ * 2))

  and
  CHANGED THIS
  println(listOfInt.flatMap(new Function[Int, MyList[Int]] {
    override def apply(elem: Int): MyList[Int] = new Cons(elem, new Cons[Int](elem + 1, Empty))
  }))
  TO
  println(listOfInt.flatMap(elem => new Cons(elem, new Cons[Int](elem + 1, Empty))))
  */
  println(listOfInt.map(_ * 2))
  println(listOfInt.filter(_ % 2 == 0))

  println(listOfInt ++ bListOfInt)
  //Here _ won't work as elem is used multiple times here, so each _ maps with exact one type
  println(listOfInt.flatMap(elem => new Cons(elem, new Cons[Int](elem + 1, Empty))))

  println(clonedListOfInt == listOfInt)

  val superAdder = (x: Int) => (y: Int) => x + y
  println(superAdder(3)(4))

  //HOF
  println("< HOF functions >")
  listOfInt.foreach(println)

  println("Sorting...")
  listOfInt.add(6).sort((x, y) => x - y).foreach(print)
  println()
  listOfInt.add(6).sort((x, y) => y - x).foreach(print)

  println("\nZipWith...")
  println(listOfInt.zipWith[String, String](listOfStr.add("Imran"), _ +"-"+ _ ))

  println("Fold Function...")
  println(listOfInt.add(10).fold(0)(_ + _))

  //for comprehension implementation
  val forImpl = for {
    x <- listOfInt
    y <- listOfStr
  } yield  x +"-"+y
  println(forImpl)
}
