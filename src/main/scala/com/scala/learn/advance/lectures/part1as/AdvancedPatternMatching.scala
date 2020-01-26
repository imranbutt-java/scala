package com.scala.learn.advance.lectures.part1as

object AdvancedPatternMatching extends App {
  val numbers = List(1,2)
  val desc = numbers match {
    case head :: Nil => println(s"The only element is $head")
    case _ => println("Wildcard")
  }

  /*
  We have used pattern matching for
  - Constants
  - Wildcards
  - Case Classes
  - Tuples
  - Pattern as shown above

  NOTE:
  There is a special case when we have to make our own pattern matching. When we can't make our class as a case class
  then we have to implement unapply method. SEE BELOW
   */

  // Making class variable as val, so that they may be accessible
  // Define companion object
  // Implement unapply method, the parameter in unapply would be used for pattern and return type of unapply would
  // be considered as a matching
  class Person(val name: String, val age: Int)
  object Person {
    def unapply(person: Person): Option[(String, Int)] =
      if(person.age >= 18) Some((person.name, person.age))
      else None

    def unapply(age: Int): Option[String] =
      Some(if(age >= 18) "Adult" else "Minor")
  }

  val bob = new Person("Bob", 8)
  val greeting = bob match {
    // Here pattern looks for Person class unapply method, finds Person as a parameter and map return type of
    // unapply with Companion Object parameters
    // Also, note pattern matching name only matches Companion Object, that may be different from class
    case Person(n, a) => s"Name: $n and Age: $a"
    case _ => "Not for child"
  }

  //bob.age == unapply's parameter
  //status == String returned Adult/Minor
  val ageStatus = bob.age match {
    case Person(status) => s"Legal Status: $status"
  }

  println(greeting)
  println(ageStatus)

  /*
  Exercise:
  Here we have lot of if/else conditions, we have to make it elegant, concise and using pattern matching
   */
  val n: Int = 45
  val mathProperty = n match {
    case even => "Even"
    case singleDigit => "Single Digit"
    case _ => "No defined property"
  }

  // Answer
  // When some operation we have to define as a class, we should better use small letters
  object even {
    def unapply(num: Int): Option[Boolean] =
      if(num % 2 == 0) Some(true)
      else Some(false)
  }

  object singleDigit {
    def unapply(singleDigit: Int): Option[Boolean] =
      if(singleDigit < 10) Some(true)
      else Some(false)
  }

  println(mathProperty)

  //Infix Pattern
  case class Or[A,B](a: A, b: B)
  val either = Or(2, "Two")
  val numberDescription = either match {
    // Infix pattern works when we have 2 params
    //case Or(number, string) => s"In English, number $number is $string"
    case number Or string => s"In English, number $number is $string"
  }
  println(numberDescription)

  //Decomposing Sequences
  val varargs = numbers match {
    // _* is wild card varargs, means it says, whatever comes after 2 or 2,3,4...
    case List(1, _*) => "Starting with 1"
  }

  //Lets see how we would implement _* in our list
  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if(list == Empty)
        Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
    }
  }
  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    // This case looks into MyList unapply method that accepts 'myList' and returns '1,2_*' and match the sequence with
    // Cons(1, Cons(2, Cons(3, Empty)))
    case MyList(1,2,_*) => "MyList starts with 1,2"
    case _ => "Sth else"
  }

  println(decomposed)

  // Custom return types for unapply
  // Return types of unapply doesn't nessaciliary be an Option, it just need 2 functions
  // 1. isEmpty: Boolean (that returns Boolean)
  // 2. get: something ( A get method that returns anything)

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(p: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = p.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person name is $n"
    case _ => "An Alian :D"
  })

}
