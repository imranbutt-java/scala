package com.scala.learn.advance.exercies

import scala.annotation.tailrec

/* imransarwar created on 07/02/2020*/

abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B]  // prepend operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] // concatenate two streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // takes the first n elements out of this stream
  def takeAsList(n: Int): List[A] = take(n).toList()

  /*
    [1 2 3].toList([]) =
    [2 3].toList([1]) =
    [3].toList([2 1]) =
    [].toList([3 2 1])
    = [1 2 3]
   */
  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] =
    if (isEmpty) acc.reverse
    else tail.toList(head :: acc)
}

object EmptyStream extends MyStream[Nothing] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException
  def tail: MyStream[Nothing] = throw new NoSuchElementException

  def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this)
  def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  def foreach(f: Nothing => Unit): Unit = ()
  def map[B](f: Nothing => B): MyStream[B] = this
  def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this
  def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  def take(n: Int): MyStream[Nothing] = this
}

// NOTE: here we have used => for tl to CALL BY NAME, if we won't use arrow sign, then compiler would eagerly
// initiate tl and program will crash on infinite loops
class Cons[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] {
  def isEmpty: Boolean = false

  override val head: A = hd
  //Here callByName, means tl is used with lazy that makes callByNeed, Thus
  // callByName + lazy = callByNeed
  override lazy val tail: MyStream[A] = tl  // call by need
  /*
    How it works ?
    - Here EmptyStream won't be evaluated first bcz it is callByNeed
    val s = new Cons(1, EmptyStream)
    - Now with prepend s is called by Name and won't be evaluated again and same goes with ++ implementation
    val prepended = 1 #:: s = new Cons(1, s)
   */
  def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream)

  def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  /*
    s = new Cons(1, ?)
    - When map is called f would be applied to head and we get 2, but tail still wont' be evaluated and we get
      expression same as below
    mapped = s.map(_ + 1) = new Cons(2, s.tail.map(_ + 1))
    ... mapped.tail
    - tail would be evaluated unless mapped.tail is called.
   */
  def map[B](f: A => B): MyStream[B] = new Cons(f(head), tail.map(f)) // preserves lazy evaluation
  def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)
  def filter(predicate: A => Boolean): MyStream[A] =
    if (predicate(head)) new Cons(head, tail.filter(predicate))
    else tail.filter(predicate) // preserves lazy eval!

  def take(n: Int): MyStream[A] =
    if (n <= 0) EmptyStream
    else if (n == 1) new Cons(head, EmptyStream)
    else new Cons(head, tail.take(n-1))
}

object MyStream {
  // It is some sort o factory method, MyStream.from in new Cons is lazily evaluated
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new Cons(start, MyStream.from(generator(start))(generator))
}


object StreamsPlayground extends App{
  val naturals = MyStream.from(1)(_ + 1)
  print(naturals.head)
  print(naturals.tail.head)
  print(naturals.tail.tail.head)

  val startFrom0 = 0 #:: naturals // naturals.#::(0)
  print(startFrom0.head)

  println
  startFrom0.take(10).foreach(print)
  println

  // map, flatMap
  println(startFrom0.map(_ * 2).take(10).toList())
  println(startFrom0.flatMap(x => new Cons(x, new Cons(x + 1, EmptyStream))).take(10).toList())
  println(startFrom0.filter(_ < 10).take(10).take(20).toList())

  // Exercises on streams
  // 1 - stream of Fibonacci numbers
  // 2 - stream of prime numbers with Eratosthenes' sieve
  /*
    [ 2 3 4 ... ]
    filter out all numbers divisible by 2
    [ 2 3 5 7 9 11 ...]
    filter  out all numbers divisible by 3
    [ 2 3 5 7 11 13 17 ... ]
    filter out all numbers divisible by 5
      ...
   */

  /*
    [ first, [ ...
    [ first, fibo(second, first + second)
   */
  def fibonacci(first: BigInt, second: BigInt): MyStream[BigInt] =
    new Cons(first, fibonacci(second, first + second))

  println(fibonacci(1, 1).take(10).toList())

  /*
    [ 2 3 4 5 6 7 8 9 10 11 12 ...
    [ 2 3 5 7 9 11 13 ...
    [ 2 eratosthenes applied to (numbers filtered by n % 2 != 0)
    [ 2 3 eratosthenes applied to [ 5 7 9 11 ... ] filtered by n % 3 != 0
    [ 2 3 5
   */
  // eratosthenes sieve
  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] =
    if (numbers.isEmpty) numbers
    else new Cons(numbers.head, eratosthenes(numbers.tail.filter(_ % numbers.head != 0)))

  println(eratosthenes(MyStream.from(2)(_ + 1)).take(12).toList())
}