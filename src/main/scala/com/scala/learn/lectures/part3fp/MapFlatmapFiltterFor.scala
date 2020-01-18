package com.scala.learn.lectures.part3fp

/* Created by imransarwar on 2020-01-16 */
object MapFlatmapFiltterFor extends App {

  val list = List(1,2,3)
  println(list)

  //map
  println("Map...")
  println(list.map(_ + 1))
  println(list.map(_ + ": is a number"))

  //filter
  println("Filter...")
//  println(list.filter(new Function[Int, Boolean] {
//    override def apply(x: Int): Boolean = x % 2 == 0
//  }))
  println(list.filter(_ % 2 == 0))

  //flatMap
  println("Flatmap...")
  val toPair = (x: Int) => List(x, x+1)
  println(list.flatMap(toPair))

  //EXERCISE
  //print all combination between two lists
  println("Iterating...")
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("Red", "Green")
  //List(a1,a2,...d4)
  val combination = numbers.flatMap(x => chars.map(y => x+""+y))
  println(combination)
  val combination3 = numbers.filter(x => x % 2 == 0).flatMap(x => chars.flatMap(y => colors.map(z => x+""+y+"-"+z)))
  println(combination3)

  //foreach
  println("Foreach...")
  println(list.foreach(println))
  println("See the difference of list.map(_+1) VS list.foreach(_+1)")
  println(list.map(_+1))
  println(list.foreach(_+1))

  //combination3 is difficult to read, so scala provide
  //for-comprehension for better readability but it converts internally to the same flatMap and map stream
  println("For-comprehension...")
  val forCombinations = for {
    x <- numbers if x % 2 == 0 // or use numbers.filter(x => x % 2 == 0)
    y <- chars.filter(c => c == 'a')
    z <- colors
  } yield ""+x+y+"-"+z
  println(forCombinations)

  //It is identical to numbers.foreach(println)
  for {
    n <- numbers
  } println(n)

  // Syntax
  numbers.map { x =>
    x * 2
  }

  //EXERCISE
  /*
  1. MyList supports for comprehension
  To implement for comprehension for map, flatMap and filter we have below function definition
  map(f: A => B) => MyList[B]
  filter(f: A => Boolean) => MyList[A]
  flatMap(f: A => MyList[B]) => MyList[B]
  2. A small collection at most ONE element - Maybe[+T]
  -  map, flatMap, filter
   */

}
