package com.scala.learn.advance.lectures.part4implicits

import scala.util.Try

/* imransarwar created on 28/02/2020*/
// Allow us decorating existing classes, we don't have access to, with additional functions and properties
object PimpMyLibrary extends App {

  // We are going to add functionality isEven with integer values
  // 2.isPrime

//  implicit class RichInt(val value: Int) {
//    def isEven: Boolean = value % 2 == 0
//    def sqrt: Double = Math.sqrt(value)
//  }

  // Implicit classes take only and only one argument
  // We added isEven functionality with 42, that wasn't accessible
//  new RichInt(42).isEven
  // First compiler consider it as an error and then scan for all implicits that may take int
  // and apply isEven method, and then compiler make a constructor call
  // new RichInt(42).isEven
//  42.isEven

  // extends AnyVal is often done for memory optimization purposes
  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)

    // From exercise
    def times(function: () => Unit): Unit = {
      def timesAux(n: Int): Unit =
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }

      timesAux(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] =
        if (n <= 0) List()
        else concatenate(n - 1) ++ list

      concatenate(value)
    }

  }

  implicit class RicherInt(richInt: RichInt) {
    def isOdd: Boolean = richInt.value % 2 != 0
  }

  new RichInt(42).sqrt

  42.isEven // new RichInt(42).isEven
  // type enrichment = pimping

  1 to 10

  import scala.concurrent.duration._
  3.seconds

  // compiler doesn't do multiple implicit searches.
  // So, compiler would pimp 42 to RichInt and then won't go to pimp RichInt into RicherInt
  //  42.isOdd

  /*
    Enrich the String class
    - asInt
    - encrypt
      "John" -> Lqjp

    Keep enriching the Int class
    - times(function)
      3.times(() => ...)
    - *
      3 * List(1,2) => List(1,2,1,2,1,2)
   */

//  implicit class RichNewString(val str: String) extends AnyVal {
//    def asInt: Int = Integer.parseInt(str)
//  }
//  println("555".asInt + 10)



  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string) // valueOf returns java.lang.Integer and scala converts to Int
    def encrypt(cypherDistance: Int): String = string.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("3".asInt + 4)
  println("John".encrypt(2))

  3.times(() => println("Scala Rocks!"))
  println(4 * List(1,2))

  // "3" / 4
  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("6" / 2) // stringToInt("6") / 2

  // equivalent: implicit class RichAltInt(value: Int)
  // Although, the implicit conversion with methods are in general more powerful, they are discouraged
  class RichAltInt(value: Int) {
    def simple() = println("Due to implicit it is called...")
  }
  implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)

  3.simple()
  // danger zone, with implicit conversion with method, is that if some issue happens, it is very difficult
  // to trace back
  implicit def intToBoolean(i: Int): Boolean = i == 1

  /*
    if (n) do something
    else do something else
   */

  val aConditionedValue = if (3) "OK" else "Something wrong"
  println(aConditionedValue)

  // For best practice:
  /*
  - Keep type enrichment to implicit classes and type classes
  - avoid implicit defs as much as possible
  - Package implicit clearly, bring into scope only what you need
  - If you need conversions, make them specific
   */
}

