package com.scala.learn.advance.lectures.part1as

import scala.util.Try

object DarkSugar extends App {
  // 1. Syntax sugar, function with single param
  def singleArgMethod(arg: Int): String = s"$arg little duck"

  // That expression block last param would be the value for function parameter
  val desc = singleArgMethod {
    //Any code
    10
  }

  //Example 1
  //There are some similar examples, like Try companion object's apply method is called and block last expression
  // is the param value
  val tryBlock = Try {
    //...
    println("Try block")
  }

  //Example 2
  //Here both are same
  List(1,2,3).map(x => (x + 1)).foreach(print)
  println
  List(1,2,3).map { x =>
    x + 1
  }.foreach(print)
  println

  // 2. Instance of traits can be expressed as lambda expression
  // Single abstract method
  trait Action {
    def act(x: Int): Int
  }

  // Now if I hover on new Action, IDE ask me for Single Abstract Method
  // Compiler looks into instance type and look into Action class and map the lambda expression with act method.
  val actInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  val actSingleAbstractInstance: Action = (x: Int) => x + 1

  //Example 1
  //In java Runnables are used
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Java Thread")
  })

  //Now in scala for this single method, we may syntactically write it as
  val aSweetThread = new Thread(() => println("A Sweet Thread"))

  //Example 2
  abstract class AnAbstractType {
    val defined: Int = 2
    def undefinedFunc(x: Int): Unit
  }

  //Now we may create instance of abstract class by just providing the implementation
  val anImplementedType: AnAbstractType = (x: Int) => println("An implemented function")

  // 3. Special methods prepend like :: and #:: methods are special
  val prependedList = 2 :: List(1)
  //Now  as we think it would be from left to right 2 function List(1) but 2 don't have :: function
  // Actually scala says ::, last character decides associativity of Method
  // Thus instead of 2.::(List(1))
  // it is List(1).::(2)

  1 :: 2 :: 3 :: 4 :: List(5)
  //Actually it is
  List(5).::(4).::(3).::(2).::(1)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this // Actual implementation here
  }

  // So here these operators just end with colon : that is why they are right associative
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // 4. Multi-word method naming
  class TeenGirl(name: String) {
    def `said to me`(gossip: String): Unit = println(s"$name said, $gossip")
  }

  val nina = new TeenGirl("Nina")
  nina `said to me` "How are you!"

  // 5. Infix Types
  class Composite[A, B] {
    def apply(x: A, y: B): Composite[A, B] = {
      println(s"$x and $y")
      new Composite()
    }
  }
  val composite1: Composite[Int, String] = new Composite()
  val composite2: Int Composite String = new Composite()

  composite1(2, "Hello")
  composite2(3, "Bye")

  class -->[A, B] {
    def apply(x: A, y: B): A --> B = {
      println(s"$x --> $y")
      new -->()
    }
  }
  val composite3: Int --> String = new -->[Int, String]()
  val composite4: Int --> Int = new -->[Int, Int]()

  composite3(5, "Good")
  composite4(6, 7)

  // 6. update() is a special function much like apply()
  val anArray = Array(1,2,3)
  // In array it is an update method like this
  //anArray.update(2, 8) == update(index: Int, element: Type
  // Used in Mutable collection
  anArray(2) = 8
  anArray.foreach(print)
  println

  // 7. Setters for Mutable Containers
  class Mutable {
    private var internalMember: Int = 0
    def member: Int = {
      println(s"Getter is called, internalMember = $internalMember")
      internalMember
    }
    def member_=(value: Int): Unit = {
      println(s"Setter is called, internalMember = $internalMember")
      internalMember = value
    }
  }

  val aMutableContainer = new Mutable()
  aMutableContainer.member = 10
  aMutableContainer.member
}
