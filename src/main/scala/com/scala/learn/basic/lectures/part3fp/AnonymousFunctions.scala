package com.scala.learn.basic.lectures.part3fp

/* Created by imransarwar on 2020-01-13 */
object AnonymousFunctions extends App{
  //Previously using OOP
  /*
  ##### Approach 1 : Object Oriented ######
  trait MyDoubler[A] {
    def doDouble(x: A): A
  }

  val doubler = new MyDoubler[Int] {
    def doDouble(x: Int): Int = x * 2
  }

  println(doubler.doDouble(2))

  ##### Approach 2 : Function Types ######
  Approach1 is changed with the below

  val doubler = new Function1[Int, Int] {
    override def apply(x: Int): Int = x * 2
  }

  println(doubler(2))

  ##### Approach 3 : Anonymous/Lambda ######
  The above approach is changed with the below syntactical sugar

  val doubler = (x: Int) => x * 2
  println(doubler(2))

  */

  val doubler = (x: Int) => x * 2
  //The above expression can be written as below
  val tmpDoubler: Int => Int = x => x * 2
  println(doubler(2))

  //Multiple params in a lambda
  val adder = (x: Int, y: Int) => x + y
  val tmpAdder: (Int, Int) => Int = (x: Int, y: Int) => x + y

  //no params lambda
  val justDoSth = () => 3
  val tmpJustDoSth: () => 3 = () => 3


  println(s"justDoSth: $justDoSth")
  println(s"justDoSth: $justDoSth()")
  println(s"justDoSth(): ${justDoSth()}")

  println("justDoSth: "+justDoSth)     //Function Itself
  println("justDoSth(): "+justDoSth()) //Actual Call

  //Curly braces commonly used lambda expression
  val stringToInt = {(str: String) =>
    str.toInt
  }

  //To chain higher order function call, _ mostly used
  //MOAR Syntactic Sugar
  val niceIncrementer: Int => Int = _ + 1 //Equals to:  x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // Equals to: (x, y) => x + y

  //EXERCISE
  /*
  1. MyList: replace all FunctionX with lambdas
  2. Rewrite the "special" adder as an anonymous function
   */

}
