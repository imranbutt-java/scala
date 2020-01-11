package com.practice.common.lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {
  //@tailrec //Uncomment me :D
  def factorial(n: Int): Int = {
    if(n <= 1) 1
    else {
      println(s"Computing Factorial of $n, needs Factorial of ${n-1}")
      //Here stack creates new stack frame as auxiliary function is used in intermediate calculation
      val result = n * factorial(n-1)
      println(s"Computed Factorial of ${n-1}")

      result
    }
  }

  println(factorial(5))
  //Above code would crash on even at 531 recursion, so what to do ???
  //println(factorial(5000))

  def factorialOptimized(n: Int): Int = {
    @tailrec
    def factHelper(x: Int, accumulator: Int): Int = {
      if(x <= 1) accumulator
      else factHelper(x - 1, x * accumulator)
    }

    //TAIL RECURSION
    //Reason for this factorial to work, i.e. last expression of this factorial function is auxiliary function that
    // let scala to preserve the stack frame and use the same stack frame.
    factHelper(n, 1)
  }

  println(factorialOptimized(5000))
  /*
  optimizedFactorial(10) = factHelper(10, 1)
  = factHelper(9, 10 * 1)
  = factHelper(8, 9 * 10 * 1)
  = factHelper(8, 9 * 10 * 1)
  = factHelper(7, 8 *9 * 10 * 1)
    ...
  = factHelper(2, 3 * 4 * ... 9 * 10 * 1)
  = factHelper(1, 2 * 3 * 4 * ... 9 * 10 * 1)

  = 1 * 2 * 3 ... * 9 * 10

  To display this number we should use BigInt
   */

  // WHEN YOU NEED LOOP, USE TAIL RECURSION

  //ASSIGNMENT
  def concatenateTailFunc(str: String, n: Int): String = {
    @tailrec
    def concatHelper(s: String, n: Int, accumulator: String): String = {
      if(n < 1) accumulator
      else concatHelper(s, n - 1, s +" "+ accumulator)
    }

    concatHelper(str, n, "")
  }

  println(concatenateTailFunc("H", 3))

  def isPrimeTailRecFunc(n: Int): Boolean = {
    @tailrec
    def isPrimeHelper(tmp: Int, stillPrime: Boolean): Boolean = {
      if(!stillPrime) false
      else if(tmp <= 1) true
      else isPrimeHelper(tmp - 1, n%tmp != 0 && stillPrime)
    }

    isPrimeHelper(n/2, true)
  }

  println(s"Is Prime Number: ${isPrimeTailRecFunc(7)}")

  def fibonacciTailRecFunc(n: Int): Int = {
    def fibonacci(index: Int, prev: Int, beforePrev: Int): Int = {
      if (index >= n) prev
      else fibonacci(index + 1, prev + beforePrev, prev)
    }

    if(n <= 2) 1
    else fibonacci(2, 1, 1)
  }

  println("Fibonacci Tail Rec:")
  // 1 1 2 3 5 8 13 21...
  println(fibonacciTailRecFunc(6))
}
