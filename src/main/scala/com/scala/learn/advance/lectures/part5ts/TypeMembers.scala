package com.scala.learn.advance.lectures.part5ts

/* imransarwar created on 08/03/2020*/
object TypeMembers extends App {

  class Animal
  class Cat extends Animal
  class Dog extends Animal

  class AnimalCollection {
    // Type members are to help the compiler to type inference for us
    type AnimalType // abstract type member
    type BoundedAnimal <: Animal
    type SuperBoundedAnimal >: Dog <: Animal
    // This is another type member for Cat
    type AnimalC = Cat
  }
  val ac = new AnimalCollection
  // ac.AnimalType would compile but there is nothing to initialize as there is no constructor for AnimalType
  val dog: ac.AnimalType = ???
  // The same would go with BoundedAnimal, compiler never let assign Cat for BoundedAnimal
  //  val cat: ac.BoundedAnimal = new Cat

  // Compiler would let us assign Dog to pup but not any other super type of Dog
  val pup: ac.SuperBoundedAnimal = new Dog
  val cat: ac.AnimalC = new Cat

  // We use that when we have name collision in case of import from different packages
  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  // alternative to generics
  trait MyList {
    type T
    def add(element: T): MyList
  }

  class NonEmptyList(value: Int) extends MyList {
    override type T = Int
    def add(element: Int): MyList = ???
  }

  // .type
  // Sometime values types
  type CatsType = cat.type
  // Problem: Can't instantiate this type with instance rather only provide association
  // val newCat: CatsType = cat
  //  new CatsType

  /*
    Exercise - enforce a type to be applicable to SOME TYPES only
   */
  // LOCKED: Because, some other team build this API
  trait MList {
    type A
    def head: A
    def tail: MList
  }

  // This class is created that enforce to have type members as Numbers
  trait ApplicableToNumbers {
    type A <: Number
  }

  // We want this class shouldn't be able to compile when use MList {}, NOT OK
  // As this code is in our access so we let this class extend ApplicableToNumbers and it won't compile
  // class CustomList(hd: String, tl: CustomList) extends MList with ApplicableToNumbers {
  //    type A = String
  //    def head = hd
  //    def tail = tl
  //  }

  // OK
  class IntList(hd: Int, tl: IntList) extends MList {
    type A = Int
    def head = hd
    def tail = tl
  }

  // Number
  // type members and type member constraints (bounds)
}
