package com.scala.learn.basic.lectures.part2oop

import scala.language.postfixOps

/* Created by imransarwar on 2020-01-11 */
object MethodNotations extends App {
  class Person(val name: String, favMovie: String) {
    def likes(movie: String): Boolean = {
      this.favMovie == movie
    }

    def +(person: Person): String = s"$name hangout with ${person.name}"
    def unary_! : String = s"$name, What !!!"
    //For postfix we need to add the import
    //import scala.language.postfixOps
    def isAlive: Boolean = true
    //function definition is important to see
    def apply(): String = s"I am $name!!!"
  }

  val person = new Person("Umar", "Pokemon")
  println(person.likes("Pokemon"))
  //Both function calling are same
  // >>>> Infix Notation, Operator Notation == Syntactic Sugar, means nicer way to writing complex code
  //This notation only works with functions having single parameter
  println(person likes "Pokemon")

  //OPERATOR
  //Why it is infix/operator notation because function works as an operator and we can even use +, * etc
  // as a function name
  val hussain = new Person("Hussain", "The Lions King")
  println(person + hussain)
  println(person.+(hussain))

  println(1 + 2)
  println(1.+(2))

  // ALL OPERATORS ARE METHODS

  //Prefix Notation
  val x = -1
  val y = 1.unary_-
  //unary operators work with only + - ~

  println(!person)
  println(person.unary_!)

  //Postfix Notation
  //Postfix is only available for the methods without parameters
  println(person isAlive)

  //Apply
  //instance variable with parenthesis
  println(person())
}
