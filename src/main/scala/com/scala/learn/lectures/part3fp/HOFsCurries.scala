package com.scala.learn.lectures.part3fp

/* Created by imransarwar on 2020-01-14 */
object HOFsCurries extends App {

  val superFunction: (Int, (String, (Int => Int)) => Int) => (Int => Int) = null

  // Higher Order Function (HOF) : That either takes a function or returns a function
  // Like we wrote before, map, flatMap, filter

  //Now let say we want to apply n times a function on x
  //nTimes(f, n, x)
  //nTimes(f, 3, x) = f(f(f(x))) = nTimes(f, 2, f(x)) = f(f(f(x)))
  //nTimes(f, n, x) = f(f, n-1, f(x)) = f(f, n-1, f(f, n-2, f(x)))

  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if(n <= 0) x
    else nTimes(f, n-1, f(x))

  val plusOne = (a: Int) => a + 1
  val adder: Int => Int =  _ + 1
  println(s"nTimes(adder, 3, 2): ${nTimes(adder, 3, 2)}")
  println(s"nTimes(plusOne, 3, 2): ${nTimes(plusOne, 3, 2)}")

  //A better implementation of above function is to make a function that applies n times and use it
  //on any number, see:
  // Here this function is going to return lambda
  // nTimesBetter(f, n) = x => f(f(f...(x)))
  // inc3 = nTimesBetter(plusOne, 2) = 2 => plusOne(plusOne(plusOne(2)))
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) =
    if(n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x)) // < It is curried function

  val plus10 = nTimesBetter(plusOne, 10)
  println(s"plus10(2): ${plus10(2)}")

  //Curried Function
  // Below all 3 are same
  //val superAdder = (x:Int) => (y:Int) => x + y
  //val superAdder: Int => Int => Int = (x:Int) => (y:Int) => x + y
  // => Arrow is right associative
  val superAdder: Int => (Int => Int) = (x:Int) => (y:Int) => x + y
  val add5 = superAdder(5)
  println(s"add5(10): ${add5(10)}")
  println(s"superAdder(5)(10): ${superAdder(5)(10)}")

  // FUNCTION WITH MULTIPLE PARAMETER LIST
  // Provide Double and we conver it to String
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val standardFormatter: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormatter: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormatter(Math.PI))
  println(preciseFormatter(Math.PI))

  //EXERCISE
  /*
  Expand MyList
  - foreach method A => Unit
    > it would print all values 1 \n 2 \3
    [1,2,3].foreach(x => println(x))

  - sortFunction((A,A) => Int) => MyList
    [1,2,3].sort((x,y) => y-x) => [3,2,1]

  - zipWith(list, (A,A) => B) => MyList[B]
    [1,2,3].zipWith([4,5,6], x * y) => [1 * 4], [2 * 5], [3 * 6] => [4,10,18]

  - fold(start)(function) => a value
    [1,2,3].fold(1)(x+y) => 5

   */

}
