package com.scala.learn.advance.lectures.part5ts

/* imransarwar created on 04/03/2020*/
object RockingInheritance extends App {

  // Say writing an API for IO
  // convience
  trait Writer[T] {
    def write(value: T): Unit
  }
  trait Closeable {
    def close(status: Int): Unit
  }
  // GenericStream that may also mixes with other traits
  trait GenericStream[T] {
    // some methods
    def foreach(f: T => Unit): Unit
  }

  // If we are expecting GenericStream with other traits, and we don't know who actually processed the Stream,
  // So we may mix traits as below and can access any function from all traits
  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.foreach(println)
    stream.close(0)
  }

  // From inheritance, we have
  // diamond problem

  trait Animal { def name: String }
  trait Lion extends Animal { override def name: String = "lion" }
  trait Tiger extends Animal { override def name: String = "tiger" }
  // Here comes diamond problem
  class Mutant extends Lion with Tiger
  // It is clear, form Mutant override and there is no diamon problem
//  class Mutant extends Lion with Tiger {
//    override def name: String = "Alien"
//  }

  /*
    Mutant
    extends Animal with { override def name: String = "lion" }
    with { override def name: String = "tiger" }

    NOTE: LAST OVERRIDE GETS PICKED, that is why it prints Tiger !
   */
  val m = new Mutant
  println(m.name)



  // the super problem + type linearization

  trait Cold {
    def print = println("cold")
  }

  trait Green extends Cold {
    override def print: Unit = {
      println("green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print: Unit = {
      println("blue")
      super.print
    }
  }

  class Red {
    def print = println("red")
  }

  class White extends Red with Green with Blue {
    override def print: Unit = {
      println("white")
      // Here super print would print all except Red :O
      // White = AnyRef with <Red> with <Cold> with <Green> with <Blue> with <White>
      // The above is called type linearization
      // In short, compiler would see the last trait and would its maximum parent
      // and Red never comes in that tree.
      super.print
    }
  }

  val color = new White
  color.print

}