package com.scala.learn.interview.fp

/* imransarwar created on 08/08/2020*/
object PrimeNumbers extends App {
  val primes: LazyList[Int] = 2 #:: LazyList.from(3)
      .filter(x => {
          val sqrtNum = primes.takeWhile(y => y <= Math.sqrt(x))
          !sqrtNum.exists(y => x % y == 0)
        }
      )
  def primeNumatAt(position: Int): Int = {
    if(position > 0) primes.take(position).toList.last
    else -1
  }

  println("# PrimeNumber")
  println(primes.take(10).toList)
  println(primeNumatAt(3))
}
