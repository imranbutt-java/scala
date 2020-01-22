package com.scala.learn.basic.lectures.part2oop

/* Created by imransarwar on 2020-01-11 */
object Objects extends App {
  //>>> SCALA doesn't have CLASS-LEVEL functionality, no concept of "static"
  //Scala has something object level, and object has no parameter
  object Person {
    //class/static/singleton level functionality
    val N_EYES: Int = 2
    def canFly: Boolean = false
    def from(mother: Person, father: Person): Person = new Person("Bob")
    def apply(mother: Person, father: Person): Person = new Person("Bob")
  }
  // Companion Design
  // When in the same scope static/signleton level and regular instance level functionality defined, means as we
  // have defined 'class Person' in the same scope.
  // What we acheive with it
  // We split functionality and get both within same scope
  // Can access eachother private members
  // That Scala more OO compared to Java, because not concept of accessing class variables
  class Person(val name: String) {
    //Instance-level Functionality
  }

  println(Person.N_EYES)
  println(Person.canFly)

  //NOTE
  //Scala Object = Singleton instance
  val umar = Person
  val hussain = Person
  println(umar == hussain)

  //Also note when we instantiate class Person that instance is different from object Person
  val john = new Person("John")
  val marry = new Person("Marry")
  println(john == marry)

  //Factory method in Sigleton are defined as normal functions and apply is also the same.
  val baby = Person.from(john, marry)
  val baby2 = Person.apply(john, marry)
  //Here see object is callable like a function !!
  val baby3 = Person(john, marry)

  //Scala Application == Scala object with main method
  //Unit here is equal to java void
  //def main(args: Array[String]): Unit
}
