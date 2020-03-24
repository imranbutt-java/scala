package com.scala.learn.advance.exercies

import com.scala.learn.advance.lectures.part4implicits.TypeClasses.User

/* imransarwar created on 27/02/2020*/
object EqualityPlayground extends App {

  /**
   * Equality
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  /*
  Exercise: implement the TC pattern for the Equality tc.
 */
  // NameEquality is turned to implicit bcz of Equal[T]
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val john = User("John", 32, "john@rockthejvm.com")
  val anotherJohn = User("John", 45, "anotherJohn@rtjvm.com")
  // AD-HOC polymorphism, which is different from Polymorphism, so here in Equal we may call 2
  // different classes for equality comparison, that is why it is called polymorphic

  /*
    Exercise - improve the Equal TC with an implicit conversion class
    ===(anotherValue: T)
    !==(anotherValue: T)
   */

  implicit class TypeSafeEqual[T](value: T) {
    def ===(other: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value, other)
    def !==(other: T)(implicit equalizer: Equal[T]): Boolean = ! equalizer.apply(value, other)
  }

  println(john === anotherJohn)
  /*
  How compiler came to conclusion using implicit
    john.===(anotherJohn)
    new TypeSafeEqual[User](john).===(anotherJohn)
    new TypeSafeEqual[User](john).===(anotherJohn)(NameEquality)
   */
  /*
    TYPE SAFE
   */
  println(john == 43)
  //  println(john === 43) // TYPE SAFE
}
