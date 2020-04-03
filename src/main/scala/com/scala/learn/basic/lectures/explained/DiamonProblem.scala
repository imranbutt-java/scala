package com.scala.learn.basic.lectures.explained

/* imransarwar created on 02/04/2020*/
object DiamonProblem extends App {

  /**
   * Right most class' function would be used in case of solution
   */
  trait Animal {
    def eat(): Unit = println("A: chew chew")
  }

  trait Dog extends Animal {
    override def eat(): Unit = println("D: chew chew")
  }

  trait GermanShepherd extends Dog {
    override def eat(): Unit = println("G: chew chew")
  }

  // In this class, we expect Dog's eat function would be called but we would get GermanShepherd's
  class UnknownDogBreed extends Dog with Animal with GermanShepherd {
  }

  val unkownDogBreed = new UnknownDogBreed()
  unkownDogBreed.eat()
}
