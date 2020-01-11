package com.scala.learn.lectures.part1basics

object CallBVvsCallBN extends App {
  def callByValue(time: Long): Unit = {
    println(s"By Value: $time")
    println(s"By Value: $time")
  }

  //Used in Lazy evaluation
  def callByName(time: => Long): Unit = {
    println(s"By Name: $time")
    println(s"By Name: $time")
  }

  //In callByValue System.nanoTime() is calculated first and then function is called
  //callByValue(16623704863825L)
  //In callByName System.nanoTime() is passed as it is to callByName
  //callByName(System.nanoTime())
  callByValue(System.nanoTime())
  callByName(System.nanoTime())

  //Another good example where byName is not evaluated first
  def infinite(x: Int): Int = 1 + infinite(x)
  def printFirst(x: Int, y: => Int): Unit = println(x)

  //It crashed before calling printFirst function bcz infinite(20) was calculating value at line:27
  //printFirst(infinite(20), 21)
  //Here infinite(20) was not called in printFirst() function so no stack overflow
  printFirst(21, infinite(20))
}
