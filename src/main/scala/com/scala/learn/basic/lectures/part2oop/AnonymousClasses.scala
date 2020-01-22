package com.scala.learn.basic.lectures.part2oop

/* Created by imransarwar on 2020-01-12 */
object AnonymousClasses extends App {
  abstract class Animal {
    def eat: Unit
  }

  val funnyAnimal = new Animal {
    override def eat: Unit = println("HeeeHaaa")
  }

  println(funnyAnimal.getClass)

}
