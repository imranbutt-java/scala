package com.scala.learn.advance.lectures.part4implicits

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/* imransarwar created on 02/03/2020*/
object MagnetPattern extends App {
  // Magnet Pattern is a use case of type classes solving the problems created by
  // method overloading

  // Suppose, we are working on api, remote peer to peer communication protocol. All the actors in your
  // network are identical and can handle various types of messages.

  class P2PRequest
  class P2PResponse
  class Serializer[T]


  trait Actor {
    def receive(statusCode: Int): Int
    def receive(request: P2PRequest): Int
    def receive(response: P2PResponse): Int
    def receive[T : Serializer](message: T): Int
    def receive[T : Serializer](message: T, statusCode: Int): Int
    def receive(future: Future[P2PRequest]): Int
    //    def receive(future: Future[P2PResponse]): Int
    // lots of overloads
  }

  /*
    1 - type erasure ( Generic types are erased at compile time, and function only expect Future )
    2 - If we want some HOFs, lifting doesn't work for all overloads

      val receiveFV = receive _ // ?! So, by _ what does it means, which receive method ??

    3 - code duplication, so for all receive method we may duplicate codes
    4 - type inferrence and default args
      actor.receive(?!) // Compiler won't be able to decide what default arg for which method
   */

  trait MessageMagnet[Result] {
    def apply(): Result
  }

  // Single receive method, act for all
  def receive[R](magnet: MessageMagnet[R]): R = magnet() // magnet() means magnet.apply()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int] {
    def apply(): Int = {
      // logic for handling a P2PRequest
      println("Handling P2P request")
      42
    }
  }

  implicit class FromP2PResponse(response: P2PResponse) extends MessageMagnet[Int] {
    def apply(): Int = {
      // logic for handling a P2PResponse
      println("Handling P2P response")
      24
    }
  }

  // implicit called: Compiler search for receive method that takes P2PRequest, it looks what MessageMagnet
  // is taking P2PRequest as a parameter and when it finds implicit of that, after that magnet() calls
  // FromP2PRequest apply method
  // apply overload are now in different classes
  receive(new P2PRequest)
  receive(new P2PResponse)

  // Using Magnet Pattern, we have some pros and cons
  // 1 - no more type erasure problems!
  // Ans: This become possible, because compiler looks for implicit conversion before the type erased
  implicit class FromResponseFuture(future: Future[P2PResponse]) extends MessageMagnet[Int] {
    override def apply(): Int = 2
  }

  implicit class FromRequestFuture(future: Future[P2PRequest]) extends MessageMagnet[Int] {
    override def apply(): Int = 3
  }

  println(receive(Future(new P2PRequest)))
  println(receive(Future(new P2PResponse)))

  // 2 - lifting works
  // Suppose, there is a MathLib and we are working on addition api
  trait MathLib {
    def add1(x: Int): Int = x + 1
    def add1(s: String): Int = s.toInt + 1
    // add1 overloads
  }

  // "magnetize", Note below AddMagnet, no type parameter is passed
  trait AddMagnet {
    def apply(): Int
  }

  def add1(magnet: AddMagnet): Int = magnet()

  implicit class AddInt(x: Int) extends AddMagnet {
    override def apply(): Int = x + 1
  }

  implicit class AddString(s: String) extends AddMagnet {
    override def apply(): Int = s.toInt + 1
  }

  // Here using lifting we may call HOFs
  // here compiler is not using underscore _ , because, we haven't mentioned type parameter in trait AddMagnet
  val addFV = add1 _
  println(addFV(1))
  println(addFV("3"))

  // Why compiler can't compile the below line, because, for receive method 'Result' is a generic type and
  // compiler couldn't decide what type we are expecting, but for 'add1' apply() return type is concrete type
  // Int so it get compiled
  //  val receiveFV = receive _
  //  receiveFV(new P2PResponse)

  /*
    Drawbacks
    1 - verbose
    2 - harder to read
    3 - you can't name or place default arguments // receive is always expecting something type of magnet
    4 - (hard to find bugs) call by name doesn't work correctly
    (exercise: prove it!) (hint: side effects)
   */

  class Handler {
    // s: => String , call by name
    def handle(s: => String) = {
      println(s)
      println(s)
    }
    // other overloads
  }

  trait HandleMagnet  {
    def apply(): Unit
  }

  def handle(magnet: HandleMagnet) = magnet()

  implicit class StringHandle(s: => String) extends HandleMagnet {
    override def apply(): Unit = {
      println("Apply method is called...")
      println(s)
    }
  }

  def sideEffectMethod(): String = {
    println("Hello, Scala")
    "Hello"
  }

  //  handle(sideEffectMethod())
  handle {
    println("Hello, Scala")
    // 'magnet' would actually an implicit call new StringHandle("magnet")
    "magnet"
  }
  // careful, using magnet pattern when using expressions or side effects are used.
  // it is hard to trace also...
}
