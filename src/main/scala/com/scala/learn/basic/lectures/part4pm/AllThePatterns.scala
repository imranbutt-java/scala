package com.scala.learn.basic.lectures.part4pm

import com.scala.learn.basic.exercies.{Cons, MyList, Empty}

object AllThePatterns extends App {

  //Constants: which are background in switch on steroids
  val x: Any = "Scala"
  val constants = x match {
    case 1 => 1
    case "Scala" => "The Scala"
    case true => 1
    case AllThePatterns => "The Singleton Object"
  }

  // 2. Match Anything
  // 2.1 Wild card
  val matchAnyThing = x match {
    case _ =>
  }

  // 2.2 Variable
  val matchAVariable = x match {
    case something => s"I have found $something"
  }

  // 3. Tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1,1) =>
    case (sth, 2) => s"Extractor resolve sth if rest of expression matches sth == $sth"
  }

  println(matchATuple)
  //Pattern Matching can be nested
  val aNestedTuple = (1, (2,3))
  val matchNestedTuple = aNestedTuple match {
    case (_, (2, sth)) => s"Found sth == $sth"
  }

  println(matchNestedTuple)

  // 4. case classes - constructor pattern
  val aList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val matchAList = aList match {
    case Empty => s"Found $Empty"
    case Cons(head, tail) => s"Extractor Patter populate the value of head n tail: head: $head n tail: $tail"
    case Cons(head, Cons(subHead, subTail)) => s"subHead: $subHead and subTail: $subTail"
  }

  println(matchAList)

  // 5. List Patterns
  val aStdList: List[Int] = List(1,2,3,7)
  val matchStdList = aStdList match {
    case List(1, _,_,_) => "Extractor"//Extractor, List pattern matched though List is not a case class
    case List(1, _*) => "varargs"//Var Args, List of arbitrary length
    case 1 :: List(_) => "infix"// Infix Pattern
    case List(1,2,3) :+ 7 => ""// Also infix Pattern
  }

  println(matchStdList)

  // 6. Type Specifier
  val unknown: Any = List(2)
  val matchUnknown = unknown match {
    case list: List[Int] => "Found List[Int] with Type Specifier"
    case _ => "Found Nothing"
  }

  println(matchUnknown)

  // 7. Name Binding
  // A pattern is given a name that could be used here or later
  val matchNameBinding = aList match {
    case nonEmptyList @ Cons(_,_) => "Empty"//Name binding = Use it later or here
    case Cons(1, rest @ Cons(2, _)) => "Name Binding"//Name binding inside nested patterns
  }

  println(matchNameBinding)

  // 8. Multi Pattern
  val matchMultiPattern = aList match {
    case Empty | Cons(0, _) => "Multi Pattern"// Compound Pattern == Multi Pattern
    case _ => ""
  }

  // 9. if guard
  val matchIfGuard = aList match {
    case Cons(_, Cons(evenElem, _)) if evenElem % 2 == 0 => "If Guard"// If guard applied
    case _ => ""
  }

  // QUESTION
  val numbers = List(1,2,3)
  val matchNumbers = numbers match {
    case listOfStr: List[String] => "List of String"
    case listOfNum: List[Int] => "List of Numbers"
    case _ => ""
  }

  println(matchNumbers)
  //NOTE: answer would be "List of String", means it is a fault of JVM becaue, JVM is compatible to run even Java 1
  // when there were no generics, So JVM after pattern match get rid of generics and above code looks like
  // case listOfStr: List => "List of String"
  // case listOfNum: List => "List of Numbers"
  // It is called type eraser
}

