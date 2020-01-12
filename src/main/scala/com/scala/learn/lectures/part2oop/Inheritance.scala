package com.scala.learn.lectures.part2oop

/* Created by imransarwar on 2020-01-11 */
object Inheritance extends App {
  //Like Java single class inheritance
  //Private method are not accessible
  sealed class Animal {
    val creature: String = "Wild"
    def eat = println("nomnom")
    private def walk = println("Dugdug")
    //only accessible in child also
    protected def jump = println("shooom")
    //final never let the child class to override the function
    final def see = println("See")
  }

  final class Cat extends Animal {
    def checkProtectedFunc = {
      jump
    }
  }

  val cat = new Cat
  cat.eat
  //cat.walk
  cat.checkProtectedFunc

  //Constructors
  //As JVM in inheritance calls constructor of parent than of Child
  class Person(name: String, age: Int)
  //This statement won't work
  //class Adult(name: String, age: Int, id: String) extends Person
  //thus, need to provide name and age to parent
  class Adult(name: String, age: Int, id: String) extends Person(name, age)

  //Override
  class Dog(override val creature: String) extends Animal {
    //class variables could be overriden in class variables
    override def eat = {
      println("Super is called...")
      super.eat
      println("cutch cutch")
    }
  }

  val dog = new Dog("K9")
  dog.eat

  //Type substitution == broadly polymorphism
  val animal: Animal = new Dog("Pitbul")
  animal.eat

  //Preventing overrides
  //- 1. Use final keyword
  //- 2. final keyword could be used with class
  //- 3. Sealed the class = extend classes in this file, prevent extension in other files
  // Sealed used in case when types are so exhaustive

}
