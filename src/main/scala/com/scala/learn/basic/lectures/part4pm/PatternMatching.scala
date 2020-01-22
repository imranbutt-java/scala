package com.scala.learn.basic.lectures.part4pm

import scala.util.Random

object PatternMatching extends App {
  //Switch on Steroids
  val random = new Random
  val x = random.nextInt(10)

  val desc: String = x match {
    case 1 => "One"
    case 2 => "Two"
    case 3 => "Three"
    case _ => "Something more than 3" // _ is a wild card
  }

  println(s"$x : $desc")

  /*
  Pros:
  1. It helps in decompose values, specially in case classes
  2. If no pattern matched, a MatchError is thrown, so better to keep Wild Card
  3. Return type of match expression is achieved by unified return types of all cases
  4. Pattern Matching works really good with case classes
   */
  // 1. Decompose Values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 8)

  val greetings = bob match {
    case Person(n, a) => s"My name $n and age $a years old"
    case _ => "Who am I ?"
  }

  println(greetings)
  //See if we use sealed class, Pattern Match with extractor complains about the case
  //PM on sealed hierarchies, so sealed classes cover us from using bad pattern matching, during compile time
  /*
  We get warning when we compile
  Warning:(43, 3) match may not be exhaustive.
          It would fail on the following inputs: Animal(), Parrot(_)
          animal match {
   */
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("German Shepherd")
  animal match {
    case Dog(breed) => println(s"Case for breed $breed")
  }

  //But don't overkill with pattern matching, like I may find even number as below
  val isEven = x % 2 == 0
  // Why to use pattern matching here
  val isEvenPM = x match {
    case n if(n % 2 == 0) => true
    case _ => false
  }

  // EXERCISE
  /*
    Simple function use PM
    takes an Expression => Human Readable format

    Sum(Number(2), Number(3)) => 2 + 3
    Sum(Number(2), Number(3), Number(4)) => 2 + 3 + 4
    Prod(Sum(Number(1), Number(2)), Number(3)) => (1 + 2) * 3
    Prod(Number(1), Sum(Number(2), Number(3))) => 1 * (2 + 3)
   */
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def mightShowParenthesis(expr: Expr): String = expr match {
        case Number(_) => show(expr)
        case Prod(_, _) => show(expr)
        case _ => "( " + show(expr) + " )"
      }

      mightShowParenthesis(e1) + " * " + mightShowParenthesis(e2)
    }
  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Prod(Sum(Number(1), Number(2)), Number(3))))
  println(show(Prod(Number(1), Sum(Number(2), Number(3)))))

}
