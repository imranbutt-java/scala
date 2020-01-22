package com.scala.learn.basic.lectures.part3fp

/* Created by imransarwar on 2020-01-13 */
object WhatsAFunction extends App {
  // Use function as a first class members
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  //doubler works as a function, which is a instance of class
  println(doubler(2))

  // Function Types = Function1[A,B] => scala provides 22 Functions already
  val stringToIntConverter = new Function[String, Int] {
    override def apply(str: String): Int = str.toInt
  }

  println(stringToIntConverter("7") + 3)

  // Using Function 2 where we pass 3 params
  //Syntactic Sugar for such function
  //Function2[x, y, r] === (x, y) => r
  //val adder = new Function2[Int, Int, Int] {
  val adder = new ((Int, Int) => Int) {
    override def apply(a: Int, b: Int): Int = a + b
  }

  //ALL SCALA FUNCTIONS ARE OBJECTS

  println(s"Using Adder: $adder(2, 3)")

  //EXERCISE
  /*
  1. A function which takes 2 strings and concatenates them
  2. Transform MyPredicate and MyTransformer into Function Types
  3. Define a function that takes an int and returns another function which takes an int and returns an int
   */

  val concatenator = new ((String, String) => String) {
    override def apply(x: String, y: String): String = x.concat(y)
  }

  println(s"Concatenator: ${concatenator("12", "ab")}")
  //val superAdder = (x: Int) => (y: Int) => x + y
  //Above Lambda expression for below function
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Int => Int = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = superAdder(3)
  //See how superadder may be called
  println(adder3(5))
  println(superAdder(3)(5)) //Curried Function, that takes multi parameters ...


}

trait MyFunction[A, B] {
  def apply(element: A): B
}
