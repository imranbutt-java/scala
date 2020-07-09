package com.scala.learn.interview.numbers

/* imransarwar created on 09/07/2020*/
object NumberOps {
  implicit class RRichInt(n: Int) {
    // Complexity O(sqrt(n))
    /*
    isPrime(11) = ip(2)
    = 11 % 2 != 0 && ip(3)
    = ip(3)
    = 11 % 3 != 0 && ip(4)
    = ip(4 > sqrt(abs(11))
    = false
     */
    def isPrime: Boolean = {
      def isPrimeTailrec(divisor: Int): Boolean = {
        if(divisor > Math.sqrt(Math.abs(n))) true
        else n % divisor != 0 && isPrimeTailrec(divisor + 1)
      }
      if(n < 2) false
      else isPrimeTailrec(2)
    }

    // Wrong solution
    def isPrime2: Boolean = {
      def isPrimeUntil(tmp: Int): Boolean = {
        if(tmp <= 1) true
        else n % tmp != 0 && isPrimeUntil(tmp-1)
      }
      isPrimeUntil(n / 2)
    }

    def isPrime3: Boolean = (n > 1) && ! Range(2, n-1).exists(n % _ == 0)
  }
}

object PrimeNumberImplicit extends App {
  import NumberOps._

  // Decompose to its constituent prime divisor
  def findPrimeDivisor(n: Int): List[Int] = {
    assert(n > 1, "Number should be greater than 1.")
    def findPrimeDivisorTailrec(divisor: Int, acc: List[Int]): List[Int] = {
      if(divisor < 2) acc
      else if(n % divisor == 0 && divisor.isPrime) findPrimeDivisorTailrec(divisor - 1, divisor :: acc)
      else findPrimeDivisorTailrec(divisor - 1, acc)
    }
    findPrimeDivisorTailrec(n, List())
  }

  println("# Is Prime Number")
  println(9.isPrime3)
  println(11.isPrime3)
  println(1.isPrime3)
  println(0.isPrime3)

  println(9.isPrime)
  println(11.isPrime)
  println(1.isPrime)
  println(0.isPrime)

  println("# Decompose")
  println(findPrimeDivisor(22))
}
