package com.scala.learn.basic.lectures.hof

/* imransarwar created on 19/03/2020*/
object MapAndFlatMap extends App {
  /*
   Map is like a math operation on sets
   Suppose we have sets A = {2, 3} and B = {4, 9, 10}
   and in maths we say
   Relation in mathematics
   f: A => B
   Read it as (f is a function such that it maps A to B)
   Now say f = x ^ 2
   Thus if we apply f on A it would map with some elements from B {4, 9}
   */
  val list = List(2, 3)
  val ans = list.map(x => math.pow(x, 2))
  println(ans)

  /*
  Flatmap is like it flattens the hierarchy level by one level each time
  It combines the result into might be another type
   */
  case class Person(age: Int)
  val nums = List(1,2,3)
  def f(x: Int) = List(x + 1, x + 2)
  def g(x: Int): Map[Int, Int] = Map[Int, Int](1 -> (x + 1), 2 -> (x + 2))
  def h(x: Int): Person = Person(x)
  val flatMapAns = nums.flatMap(x => f(x))
  val mapAns = nums.map(x => f(x))

  println(s"flatMapAns: $flatMapAns")
  println(s"mapAns: $mapAns")

  val flatMapOtherTypeAns = nums.flatMap(x => g(x))
  val mapOtherTypeAns = nums.map(x => g(x))

  println(s"flatMapOtherTypeAns: $flatMapOtherTypeAns")
  println(s"mapOtherTypeAns: $mapOtherTypeAns")

  // Now flatMap won't compile, check the return types of both functions
  // val flatMapPersonAns = nums.flatMap(x => h(x))
  // but if we use List as a return type flatMap code would compile
  val flatMapPersonAns = nums.flatMap(x => List(h(x)))
  val flatMapPersonAns2 = nums.flatMap(x => Option(h(x)))
  val mapPersonAns = nums.map(x => x -> h(x))
  val mapPersonAns2 = nums.map(x => Option(h(x)))

  println(s"flatMapPersonAns $flatMapPersonAns")
  println(s"mapPersonAns $mapPersonAns")
  println(s"flatMapPersonAns2 $flatMapPersonAns2")
  println(s"mapPersonAns2 $mapPersonAns2")

}
