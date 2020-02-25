package com.scala.learn.advance.lectures.part4implicits

/* imransarwar created on 25/02/2020*/
object ImplicitsIntro extends App {
  /*
   This syntax compiles
    - operator acts as a function
    - left operator is considered an instance and operator works as a function
    - Note: there is no -> method in string class
    >> Here comes implicits
    - Here implicit -> converts the first argument to ArrowAssoc class
   */
  val pair = "Imran" -> "777"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi, my name is $name !"
  }

  implicit def convertStringToPerson(str: String): Person = Person(str)

  println("Peter".greet) // println(fromStringToPerson("Peter").greet)

  //  class A {
  //    def greet: Int = 2
  //  }
  //  implicit def fromStringToA(str: String): A = new A

  // implicit parameters
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount = 10

  // Here the 2nd argument is passed by compiler using implicits,
  // if I turn defaultAmount = "10", there would compilation error in calling increment
  increment(2)
  // NOT default args
}
