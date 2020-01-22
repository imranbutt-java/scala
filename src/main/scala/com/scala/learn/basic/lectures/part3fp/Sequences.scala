package com.scala.learn.basic.lectures.part3fp

import scala.util.Random

/* Created by imransarwar on 2020-01-18 */
object Sequences extends App {

  //Sequences general interface for data structures, that have 2 properties
  // 1. Have a well defined order
  // 2. Can be indexed
//  trait Seq[+A] {
//    def head: A
//    def tail: Seq[A]
//  }
  //Seq, has an apply factory method that can create sub-classes of Seq
  val sequences = Seq(4,2,3,1)
  println(sequences)
  println(sequences.reverse)
  println(sequences(2))
  println(sequences ++ Seq(5,6,7))
  println(sequences.sorted)

  //Ranges
  println("@Ranges...")
  val aRange: Seq[Int] = (1 to 10)
  aRange.foreach(print); println()

  (1 until 10).foreach(x => print(x))

  //List
  // A LinearSeq immutable linked list
  // Head, tail and isEmpty O(1)
  // other most operations O(n)
  // Sealed has 2 subtypes
  // 1. Object Nil (empty)
  // 2. class ::
  println("\n@List...")
  val aList = List(1,2,3)
  val prepended = 30 :: aList
  println(prepended)
  println(40 +: aList :+ 50)

  //Curried Function
  val apple5 = List.fill(5)("Apple")
  println(apple5)
  println(aList.mkString("-|-"))

  //Arrays
  println("Arrays...")
  //Same as java arrays
  // 1. Mutable
  // 2. Indexing Fast
  val numbers = Array(1,2,3,4)
  val threeNum = Array.ofDim[Int](3)
  val threeStr = Array.ofDim[String](3)
  numbers.foreach(print)
  println()
  threeNum.foreach(print)
  println()
  threeStr.foreach(x => print(s"$x "))
  println()

  //Mutation
  numbers(2) = 0 // Syntax sugar, actually numbers.update(2, 0)
  numbers.foreach(print); println()

  //Arrays to Seq
  val numberSeq: Seq[Int] = numbers //Implicit Conversion
  println(numberSeq)

  //Vector
  println("Vector...")
  /*
    1. Default implementation for immutable sequence
    2. Effective constant index read/write: Olog(n)
    3. Fast Element Addition
    4. Implemented as fixed-branched trie (branch factor 32)
    5. Good performance for large sizes
   */
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  //Finding time consumed for write operation
  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = Random
    val times = for {
      itr <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }
    times.sum * 1.0 / maxRuns
  }
  val numList: List[Int] = (1 to maxCapacity).toList
  val numVector: Vector[Int] = (1 to maxCapacity).toVector
  /*
    List:-
    pros: keeps reference to tail, means updating an element at the head is fast
    cons: updating an element in the middle takes time
   */
  println(s"Write Time for List: ${getWriteTime(numList)}")
  /*
    Vector:-
    pros: Depth of the tree is small
    cons: Needs to replace an entire 32-element chunk
   */
  println(s"Write Time for Vector: ${getWriteTime(numVector)}")
 }
