package com.scala.learn.basic.lectures.part1basics

object ValuesVariablesTypes extends App {
  //Inferred by Compiler
  //Immutable
  val x = 2
  println(x)

  // String
  val str:String = "Hello Scala"
  println(str)

  val aShort: Short = 22
  val aBoolean: Boolean = true
  val aChar: Char = '2'
  val aLong: Long = 12121212121L
  val aFloat: Float = 1f
  val aDouble: Double = 132232.23232

  //variables, they are side effects in Scala
  var aVar: Int = 10
  aVar = 12

}
