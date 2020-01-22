package com.scala.learn.basic.lectures.part4pm

object PatternsEverywhere extends App {
  // Big idea # 1
  // Catches are matches :D
  try {
    throw new NullPointerException
  } catch {
    case e: RuntimeException => println("rte")
    case e: NullPointerException => println("npe")
    case _ => println("Exception")
  }
  
  /*
  The above actually works sth like this
  try {
    throw new NullPointerException
  } catch(e) { 
    e match {
      case e: RuntimeException => println("rte")
      case e: NullPointerException => println("npe")
      case _ => println("Exception")
    }
  }
  */

  // Idea # 2
  // Generators are also pattern matching
  val list = List(1,2,3,4)
  val evenOnes = for {
    x <- list if x % 2 == 0
  } yield x * 10

  println(evenOnes)

  // Tuples
  // See how the values first and second are decomposed, it is bcz of pattern matching
  val aTuples = List((1,2), (3,4))
  val tuplesMatch = for {
    (first, second) <- aTuples
  } yield first * second

  println(tuplesMatch)
  // Also same for case classes, :: Operators etc

  // Idea # 3
  // Multiple value definition based on Pattern matching
  val tuple = (1,2,3)
  val (a, b, c) = tuple
  println(s"$a , $b , $c")

  val head :: tail = list
  println(head)
  println(tail)

  // Big Idea # 4
  // Partial Function Literal
  val mappedList = list.map {
    case x if x % 2 == 0 => "Even"
    case 1 => "One"
    case _ => "Odd"
  }

  //this function explains what is happening in the above one
  val mappedListCopy = list.map(x => x match {
    case x if x % 2 == 0 => "Even"
    case 1 => "One"
    case _ => "Odd"
  })

  println(mappedList)
  println(mappedListCopy)


}
