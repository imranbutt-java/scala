package com.scala.learn.basic.lectures.part1basics

/* Created by imransarwar on 2020-01-11 */
object StringOps extends App {

  val str: String = "Hello, learning Scala"

  //Java Specific
  println(str.charAt(4))
  //substring first param inclusive and second exclusive
  println(str.substring(4, 8))
  println(str.split(" "))
  println(str.replace(" ", "-"))
  println(str.length)

  //Scala Specific
  val aNumString: String = "2"
  val aNumber: Int = aNumString.toInt

  println( 2  +: aNumString :+ 'z')
  println('a' +: aNumString :+ 'z')
  println('a' + aNumString + 'z')
  println(str.reverse)
  println(str.take(2))

  // S interpolation
  val name = "Ashtalfa"
  val age = 8
  val greeting = s"I am $name and $age years old"
  val birthday = s"I am $name and going to be ${age + 1} this year"

  println(birthday)

  // F interpolation
  val speed = 1.2f
  val myth = f"$name can eat bread with the speed of $speed%2.2f/minute"

  println(myth)

  // Raw interpolation
  val escaped = "This is a \n new Line"
  println(raw"This is a \n new Line")
  println(raw"$escaped")

}
