package com.scala.learn.lectures.part2oop

import com.scala.learn.lectures.part2oop.Inheritance.Dog

/* Created by imransarwar on 2020-01-11 */
object AbstractDataTypes extends App {

  //Abstract Types
  abstract class Animal {
    //abstract classes may have concrete function and variables
    val creature: String = "Wild"
    def eat: Unit
  }

  class Dog extends Animal {
    override val creature: String = "K9"
    override def eat: Unit = println("cutch cutch")
  }

  //Traits
  //Trait is similar like abstract classes but they are like java interfaces
  trait Carnivore {
    val preferedMeat: String = "Fresh Meat"
    def eat(animal: Animal): Unit
  }
  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creature: String = "croc"
    override val preferedMeat: String = "Cooked Meat"
    override def eat: Unit = println("nomnom")
    override def eat(animal: Animal): Unit = println(s"I'm croc and I eat ${animal.creature}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)
  croc.eat


  //Traits vs Abstract classes difference
  //- 1. Traits don't have constructor parameters
  //- 2. class may implement multiple traits
  //- 3. Traits == Behaviour and Abstract == Type

}
