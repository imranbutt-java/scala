package com.scala.learn.advance.lectures.part2afp

object PartialFunctions extends App {
  // All are same
  // Function1[Int, Int]
  // Int => Int
  val aFunction = (x:Int) => x + 1

  // This looks clunky function
  val oneTwoFiveClunkyFunction = (x: Int) =>
    if(x == 1) 10
    else if(x == 2) 20
    else if(x == 5) 50
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException {
    override def getMessage: String = "COMMENT ME: Function not applicable with this number"
    override def fillInStackTrace(): Throwable = super.fillInStackTrace()
  }

  //It is a total function and a Partial Function couldn't be assigned to it.
  val oneTwoFivePatternFunction = (x: Int) => x match {
    case 1 => 10
    case 2 => 20
    case 5 => 50
  }

  // Why partial function ?
  // As {1,2,5} => Int are also Int and we are returning Int

  //partial function value
  val oneTwoFivePartialFunction: PartialFunction[Int, Int] = {
    case 1 => 10
    case 2 => 20
    case 5 => 50
  }

  println(oneTwoFivePartialFunction(2))
  //println(oneTwoFivePartialFunction(9))
  println(oneTwoFivePatternFunction(5))

  // Partial Function Utilities
  println(oneTwoFivePartialFunction.isDefinedAt(9))

  // Partial functions could be lifted to total functions: Lift
  // This turns Int => Option[Int]
  val lifted = oneTwoFivePartialFunction.lift
  //Now see that lifted function won't throw exception but return None
  println(lifted(9))

  // Chain that works like orElse
  val pfChain = oneTwoFivePartialFunction.orElse[Int, Int] {
    case _ => 999
  }

  println(pfChain(2))
  println(pfChain(9))

  // Result: Partial function is a normal function
  // Note it would crash if we provide 9, so it also working as a partial function
  val oneTwoFiveTotalFunction: Int => Int = {
    case 1 => 10
    case 2 => 20
    case 5 => 50
  }

  println(oneTwoFiveTotalFunction(2))
  //HOFs accepts partial functions as well

  val oneTwoFiveHOFFunction = List(1,2,5).map {
    case 1 => 10
    case 2 => 20
    // If we would take out any case that never matches with list, it would throw exception
    case 5 => 50
  }

  println(oneTwoFiveHOFFunction)

  /*
  NOTE: Partial Functions have only one parameter type
   */

  /*
  EXERCISE:

  1. Construct a Partial Function instance yourself (anonymous class)
  2. Dumb chatbot as a Partial Function
   */

  // Answer
  // For Question 1 I wrote below but actually expected
  val myPartialFunction: PartialFunction[String, String] = {
    case "Hi" => "Hello !"
    case "What is your name?" => "My name is Imran"
    case _ => "ttyl, I am busy"
  }

  println(myPartialFunction("Hi"))

  val oneTwoFiveMyPartialFuction = new PartialFunction[Int, Int] {
    override def apply(x: Int): Int = x match {
      case 1 => 10
      case 2 => 20
      case 5 => 50
    }

    override def isDefinedAt(x: Int): Boolean = {
      x == 1 || x == 2 || x == 5
    }
  }

  println(oneTwoFiveMyPartialFuction(2))

  //Answer 2.
  //scala.io.Source.stdin.getLines().foreach(line => println(s"You said, $line"))

  val chatbot: PartialFunction[String, String] = {
    case "Hi" => "Hello !"
    case "you" => "My name is Imran"
  }

  //scala.io.Source.stdin.getLines().foreach(line => println(s"chatbot says, ${chatbot(line)}"))
  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)

}
