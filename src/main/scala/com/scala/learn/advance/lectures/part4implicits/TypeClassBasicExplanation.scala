package com.scala.learn.advance.lectures.part4implicits

import scala.concurrent.{Future, Promise}

/* imransarwar created on 23/03/2020*/
object TypeClassBasicExplanation extends App {

  // We want to create CRUD API
  //Phase 1


  // 1. Write Type Class => Could be a trait/abstract class that takes a type parameter
  // 2. Write Type Class Instances => The class that extends it and provide implementation for trait functions

  // Now to write the code like create(user) or user.create()
  // a. create object
  // b. impliment implicit methods
  // 3. Write some interface to use its functionalities

  case class User(name: String, age: Int)
  case class Car(name: String, color: String)

  trait ServiceClient[T] {
    def create(entity: T): T
  }
  implicit object UserServiceClient extends ServiceClient[User] {
    override def create(entity: User): User = User("Albert", entity.age)
  }

  implicit object CarServiceClient extends ServiceClient[Car] {
    override def create(entity: Car): Car = Car("Volkswagen", entity.color)
  }

  // Provide interface methods
  def create[T](entity: T)(implicit sc: ServiceClient[T]): T = sc.create(entity)

  val john = User("John", 21)
  val fakeFord = Car("Ford", "RED")
  println(create(john))
  println(create(fakeFord))

  // Equality
  // 1. Type Class
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  // 2. Type Class Instance
  implicit object IntEuqality extends Equal[Int] {
    override def apply(a: Int, b: Int): Boolean = a == b
  }

  object StringEuqality extends Equal[String] {
    override def apply(a: String, b: String): Boolean = a.equals(b)
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  // 3. Interface / companion object / package object
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer(a, b)
  }

  // Using Implicit Class + type class more clean usage
  implicit class TypeSafeEqual[T](value: T) {
    def ===(other: T)(implicit equalizer: Equal[T]): Boolean = equalizer(value, other)
  }

  //Appendable
  trait Appendable[A] {
    def append(a: A, b: A): A
  }
  object Appendable {
    implicit val appendableInt = new Appendable[Int] {
      override def append(a: Int, b: Int) = a + b
    }
    implicit val appendableString = new Appendable[String] {
      override def append(a: String, b: String) = a.concat(b)
    }
  }
  def appendItems[A](a: A, b: A)(implicit ev: Appendable[A]) =
    ev.append(a, b)

  //Testing

  println(Equal(2, 4))

  val bob = User("Bob", 32)
  val bob2 = User("Bob", 32)
  println(Equal(bob, bob2))
  println(bob === bob2)
  println(bob == 43)

  val res1 = appendItems(2, 3) // returns 5
  val res2 = appendItems("2", "3") // returns "23"
}