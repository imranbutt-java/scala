package com.scala.learn.lectures.part1basics

object Expressions extends App {
  val num:Int = 2 + 3
  println( 2 + 3 * 4 / 2)

  //Expressions in scala
  // + - / * (bit wise and) & | << >> (right shift with zero extension) >>>

  println( 1 != num)
  // == != >= > < <=

  var aVar = 2
  aVar += 3
  println(aVar)
  // += -= *= /= only works with var(side effects)

  //Instructions (Statement) mean do
  // vs
  // Expression means expecting some value

  val aCondition = true
  // If Expression
  val aConditionedVal = if(aCondition) 1 else 0
  println(s"ConditionedVal: ${aConditionedVal}")

  //LOOPS ARE DISCOURAGED IN SCALA DON'T WRITE THEM
  println("DON'T USE LOOP, EVERYTHING IN SCALA EXPRESSION")
  var i = 0
  while(i<3) {
    i+=1
    print(s"${i}\t")
  }

  println()
  // Reassigning values to variables return units that's why it is not recommended
  val aUnit = (i = 3) // Unit == Void
  println(s"A Unit: ${aUnit}")

  // Side Effects: these are side effect expressions
  // println, while loops, reassigning variables
  println(s"While expression return unit: ${while(1 == 2){}}")

  //Code block, Only last expression and block has local scope
  val aCodeBlock = {
    val one = 1
    val two = 2
    if (one == 1) 3 else 4
  }
}
