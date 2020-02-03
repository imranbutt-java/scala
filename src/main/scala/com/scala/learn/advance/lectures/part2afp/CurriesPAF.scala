package com.scala.learn.advance.lectures.part2afp

/**
  * Created by Daniel.
  */
object CurriesPAF extends App {

  // curried functions
  // Here it means, function takes Int and returns a function Int => Int that also takes an Int but returns Int
  // We pass to superAdder x = 3
  // returned y => x + y implies y = 3 + y
  // Now if we call function y => 3 + y, and give y = 5
  // We would get x + y = 3 + 5 = 8
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) // curried function, multiple parameter list

  // METHOD! methods are instances of classes but not instances of function themselves
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  // If we remove Int => Int, compiler would complain
  // add4 = y => 4 + y
  // Here method curriedAdder is converted into function value
  // curriedAdder expect 2 param list, but we provided one
  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION : Functions out of methods

  // functions != methods (JVM limitation)
  def inc(x: Int) = x + 1
  // List(1,2,3).map(inc)
  // So Compiler internally uses ETA-expansion and implement method as function and apply on each element as shown below
  List(1,2,3).map(x => inc(x))

  // Partial function applications
  // Here using _ means telling compiler do an ETA-Expansion and turn curriedAdder method into a function value after
  // applying value 5
  val add5 = curriedAdder(5) _ // Int => Int

  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  /*
  I tried
  val addT = simpleAddFunction(7, _)
  val add2T = simpleAddMethod(7, _)
  val add3T = curriedAddMethod(7) _
  */

  // add7: Int => Int = y => 7 + y
  // as many different implementations of add7 using the above
  // be creative!
  val add7 = (x: Int) => simpleAddFunction(7, x)  // simplest
  val add7_2 = simpleAddFunction.curried(7)
  val add7_6 = simpleAddFunction(7, _: Int) // works as well

  val add7_3 = curriedAddMethod(7) _  // PAF
  val add7_4 = curriedAddMethod(7)(_) // PAF = alternative syntax, this works with non-curried methods

  val add7_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values
                // y => simpleAddMethod(7, y)

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?") // x: String => concatenator(hello, x, howarewyou)
  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x, y) => concatenator("Hello, ", x, y)
  println(fillInTheBlanks("Daniel", " Scala is awesome!"))

  // EXERCISES
  /*
    1.  Process a list of numbers and return their string representations with different formats
        Use the %4.2f, %8.6f and %14.12f with a curried formatter function.
   */
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  //Lift this method to function val using _
  val simpleFormat = curriedFormatter("%4.2f") _ // lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(curriedFormatter("%14.12f"))) // compiler does sweet eta-expansion for us

  /*
    2.  difference between
        - functions vs methods
        - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /*
    calling byName and byFunction
    - int
    - method
    - parenMethod
    - lambda
    - PAF
   */
  byName(23)  // ok
  byName(method)  // ok
  byName(parenMethod())
  byName(parenMethod) // ok but beware ==> byName(parenMethod())
  //  byName(() => 42) // not ok
  byName((() => 42)()) // ok
  // function value is not a substitute of byName parameter
  //  byName(parenMethod _) // not ok

  //  byFunction(45) // not ok
  // Here method(is parameterless method) is evaluated to the value 42 and that 42 is not valid parameter for byFunction
  //  byFunction(method) // not ok!!!!!! does not do ETA-expansion!
  byFunction(parenMethod) // compiler does ETA-expansion, because it is a method that has parenthesis
  byFunction(() => 46) // works
  byFunction(parenMethod _) // also works, but warning- unnecessary
}

