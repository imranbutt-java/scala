package com.scala.learn.interview.numbers

import scala.util.Random

/* imransarwar created on 10/07/2020*/
// Using Monte-Carlo Algorithm approximate Pi value
object ApproximatePi extends App {
  val random = new Random(System.currentTimeMillis())
  def approximatePi(nPoints: Int): Double = {
    // Using functional composition
    val nPointsInsideCircle = (1 to nPoints).map { _ =>
      val x = random.nextDouble
      val y = random.nextDouble

      // From formula x^2 + y^2 < r^2
      x * x + y * y
    }.count(distance => distance < 1)
    nPointsInsideCircle * 4.0 /nPoints
  }

  println(s"Ref: Pi = ${Math.PI}")
  println(approximatePi(1000))
  println(approximatePi(10000))
  println(approximatePi(100000))
  println(approximatePi(1000000))
  println(approximatePi(10000000))
}
