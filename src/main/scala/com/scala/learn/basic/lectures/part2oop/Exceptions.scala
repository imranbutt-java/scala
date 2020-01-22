package com.scala.learn.basic.lectures.part2oop

/* Created by imransarwar on 2020-01-12 */
object Exceptions extends App {
  val x: String = null
  //println(x.length)
  //We get NPE

  // 1. Throwing Exception
//  val badValue: Nothing = throw new NullPointerException
//  val strBadValue: String = throw new NullPointerException

  // 2. Catching Excptions
  def getInt(wantException: Boolean): Int = {
    if(wantException) throw new RuntimeException("Can't give you a number :p")
    else 7
  }

  val potentialFailure = try {
    getInt(true)
  } catch {
    case e: RuntimeException => 8
  } finally {
    //Definitely Excecute
    //Optional
    //Never impact return type, see potentialFailure is Int
    println("In finally block")
  }

  println(potentialFailure)

  // 3. How to define your own exception
  class MyException extends Exception {
    override def getMessage: String = "It is my Exception"
  }

  //throw new MyException
  //^^ it would throw exception with the message

  //EXERCISE
  /*
  1. Crash program with OutofMemoryException
  2. Crash with StackOverflow
  3. PocketCalculator
     - add(x,y)
     - subtract(x,y)
     - multiply(x,y)
     - divide(x,y)

     Throw:
     - OverflowException if add(x,y) exceeds Int.MAX_VALUE
     - UnderFlowException if subtract(x,y) exceeds Int.MIN_VALUE
     - MathCalculationException for division by 0

   */

  // OOM
  //val array = Array.ofDim(Int.MaxValue)

  //SOF
  //def infinite: Int = 1 + infinite
  //val noLimit = infinite

  class OverflowException extends RuntimeException
  class UnderflowException extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by 0")

  object PocketCalculator {
    def add(x: Int, y: Int)= {
      val result = x + y
      if(x > 0 && y > 0 && result < 0) throw new OverflowException
      else if(x < 0 && y < 0 && result > 0) throw new UnderflowException
      else result
    }

    def subtract(x: Int, y: Int)= {
      val result = x - y
      if(x > 0 && y < 0 && result < 0) throw new OverflowException
      else if(x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def multiply(x: Int, y: Int)= {
      val result = x * y
      if(x > 0 && y > 0 && result < 0) throw new OverflowException
      else if(x < 0 && y < 0 && result < 0) throw new OverflowException
      else if(x > 0 && y < 0 && result > 0) throw new OverflowException
      else if(x < 0 && y > 0 && result > 0) throw new OverflowException
      else result
    }

    def divide(x: Int, y: Int): Unit = {
      if(y == 0) throw new MathCalculationException
      else x / y
    }
  }

//  println(PocketCalculator.add(Int.MaxValue, 1))
//  println(PocketCalculator.divide(2, 0))
}
