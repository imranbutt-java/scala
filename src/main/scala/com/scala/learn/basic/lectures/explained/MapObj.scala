package com.scala.learn.basic.lectures.explained

/* imransarwar created on 11/04/2020*/
object MapObj extends App {
  // function is applied on each element and we get another list
  // (function(n) = n + 1 ) is applied on each element
  val oneToThreeList = List(1 to 3)
  val oneToThree = List(1,2,3)

  println("# Is List(1 to 3) and List(1,2,3) same ?")
  println(oneToThree == oneToThreeList)

  val mappedList = oneToThree.map(n => n + 1)
  mappedList.foreach(n => print(n+"|"))
  println()

  def addOne(n: Int): Int = n + 1
  def mapToDouble(n: Int): Double = n * 2
  def mapToString(n: Int): String = s"$n"

  println("# Add One")
  println(List(1,2,3).map(addOne))

  println("# Convert to Double")
  println(List(1,2,3).map(mapToDouble))

  println("# Convert to String, and apply reduce to get concatenated string")
  println(List(1,2,3).map(mapToString).reduce(_+","+_))
}
