package com.scala.learn.basic.lectures.part1basics

object DefaultParams extends App {
  def factorialTailRec(n: Int, acc: Int = 1): Int =
    if(n <= 1) acc
    else factorialTailRec(n-1, n * acc)

  //But we have to pass all the time 1 as a value of Accumulator
  println(factorialTailRec(4, 1))
  //Better to use some default values and that could be overwritten
  println(factorialTailRec(4))

  //Say we have a function to save image
  def saveImage(format: String = "jpeg", width: Int =  800, height: Int =  600): Unit = println("Saved Image")

  //If I use default resolution 800x600 it would work
  saveImage("png")
  //If we provide 200, compiler get confused what param I am expecting to overwritten
  //saveImage(200)

  /*
  We have 2 solution for above issue
  1. Use default value as last param
  2. Use named parameters
   */
  saveImage(height = 1024, width = 800)

}
