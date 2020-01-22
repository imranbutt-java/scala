package com.scala.learn.basic.lectures.part2oop

/* Created by imransarwar on 2020-01-12 */
object Generics extends App {
  //that A could be named any thing, like T, D, What
  class MyList[A] {
    //used the type A
    def add[B >: A](element: B): MyList[B] = ???
    /*
    A = Cat
    B = Dog == Animal
    So we are saying in this function, if we add B in the list of A, we expect return type to be MyList[B]
     */
  }

  class MyMap[Key, Value]

  //Traits could be parameterized too with Generic
  trait Check[A] {

  }

  val listOfInt = new MyList[Int]
  val listOfString = new MyList[String]

  //Generic methods
  //Note objects can't be type parameterized like this
  //object MyList(n: Int) = {
  object MyList {
    def empty[A]: MyList[A] = new MyList[A]
  }

  val emptyListOfInt: MyList[Int] = MyList.empty[Int]

  //Variance Problem
  class Animal
  class Dog extends Animal
  class Cat extends Animal

  //yes, List[Cat] extends List[Animal] == called COVARIANCE and achieved with +A
  class CovariantList[+A]
  val animalList: CovariantList[Animal] = new CovariantList[Dog]
  //Now does that animalList can add Dog, would make the list polluted
  //animalList.add(new Dog) ??? ?
  // answer: Yes, when in covariantlist we add dog scala would convert the list to higher class, i.e. cat list would
  // be converted to animal list

  //No, == Invariance
  class InvariantList[A]
  //It shows error
  //val invariantListOfAnimal: InvariantList[Animal] = new InvariantList[Cat]
  val invariantListOfAnimal: InvariantList[Animal] = new InvariantList[Animal]

  //Hell, NO == Contravariance and it uses [-A] minus important A could be any Letter
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  //Bounded Types,
  // <: means accepts classes that are subtype of provided Bounded Type, below "Animal"
  class Cage[A <: Animal](animal: A)
  val cage = new Cage(new Dog)

  // >: means accepts classes that are supertype of provided Bounded Type, below "Animal"
  class Room[A >: Animal](animal: A)
  val animalRoom = new Room(new Dog)
}
