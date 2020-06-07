package com.scala.learn.interview.numbers

/* imransarwar created on 28/05/2020*/
object PrimeNumber extends App {
  // Complexity O(sqrt(n))
  /*
  isPrime(11) = ip(2)
  = 11 % 2 != 0 && ip(3)
  = ip(3)
  = 11 % 3 != 0 && ip(4)
  = ip(4 > sqrt(abs(11))
  = false
   */
  def isPrime(n: Int): Boolean = {
    def isPrimeTailrec(divisor: Int): Boolean = {
      if(divisor > Math.sqrt(Math.abs(n))) true
      else n % divisor != 0 && isPrimeTailrec(divisor + 1)
    }
    if(n < 2) false
    else isPrimeTailrec(2)
  }

  // Wrong solution
  def isPrime2(num: Int): Boolean = {
    def isPrimeUntil(tmp: Int): Boolean = {
      if(tmp <= 1) true
      else num % tmp != 0 && isPrimeUntil(tmp-1)
    }
    isPrimeUntil(num / 2)
  }

  def isPrime3(n: Int): Boolean = (n > 1) && ! Range(2, n-1).exists(n % _ == 0)

  // Decompose to its constituent prime divisor
  def findPrimeDivisor(n: Int): List[Int] = {
    assert(n > 1, "Number should be greater than 1.")
    def findPrimeDivisorTailrec(divisor: Int, acc: List[Int]): List[Int] = {
      if(divisor < 2) acc
      else if(n % divisor == 0 && isPrime(divisor)) findPrimeDivisorTailrec(divisor - 1, divisor :: acc)
      else findPrimeDivisorTailrec(divisor - 1, acc)
    }
    findPrimeDivisorTailrec(n, List())
  }

  println("# Is Prime Number")
  println(isPrime3(9))
  println(isPrime3(11))
  println(isPrime3(1))
  println(isPrime3(0))

  println(isPrime(9))
  println(isPrime(11))
  println(isPrime(1))
  println(isPrime(0))

  println("# Decompose")
  println(findPrimeDivisor(0))
}
