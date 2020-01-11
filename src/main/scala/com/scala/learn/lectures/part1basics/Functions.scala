package com.scala.learn.lectures.part1basics

object Functions extends App {
  //Function definition and equals expression that could be code block {} too
  def aFunc(a: String, b: String): String =
    a + " > " +b

  def bFunc(a: String, b: String): String = {
    a + " > " + b
  }

  //Parameterless Functions
  def aParamLessFunc(): Int = 32

  println("Parameterless Funciton")
  println(aParamLessFunc());
  println(aParamLessFunc)

  //Recurssive Function, Note Scala want return type :String must, and it can't infer if it is removed like
  // def aRepeatedFunc(a:String, n:Int) = {
  println("Recurrsive Function")
  def aRepeatedFunc(a:String, n:Int): String = {
    if(n == 1) " Done !"
    else s"${n}:${a} ${aRepeatedFunc(a, n-1)}"
  }

  println(aRepeatedFunc("Hi", 3))

  //WHEN YOU NEED LOOPS, USE RECURSION

  //Functions with side effects, if they return Units
  def aFuncWithSideEffects(aString: String): Unit = println(aString)

  //Code Block also let us define Auxilary functions inside
  def aOuterFunc(outerVal: Int): Int = {
    def aInnerFunc(a: Int, b: Int) : Int = a + b

    aInnerFunc(outerVal, outerVal-1)
  }

  println("Auxilary Function Call:")
  println(aOuterFunc(3))

  //ASSIGNMENT
  // Greeting Function
  println("Greeting Function")
  def greetings(name: String, age: Int): String = {
    s"Hi, my name is $name and I am $age years old."
  }

  println(greetings("David", 12))

  // Factorial Function
  println("Factorial Function")
  def factorial(num: Int): Int = {
    if(num == 0) 1
    else num * factorial(num-1)
  }

  println(factorial(1))
  println(factorial(3))

  //Fibonacci Serries
  // 1 1 2 3 5 8 13 21
  println("Fibonacci Function")
  def fibo(num: Int): Int = {
    if(num == 0 || num == 1) 1
    else fibo(num - 1) + fibo(num - 2)
  }

  println(fibo(1))
  println(fibo(3))

  //Is Prime Number, means number is not divisible by any number except itself
  println("Prime Number Function")
  def isPrime(num: Int): Boolean = {
    def isPrimeUntil(tmp: Int): Boolean = {
      if(tmp <= 1) true
      else num % tmp != 0 && isPrimeUntil(tmp-1)
    }

    isPrimeUntil(num / 2)
  }

  println(isPrime(2))
  println(isPrime(6))


}
