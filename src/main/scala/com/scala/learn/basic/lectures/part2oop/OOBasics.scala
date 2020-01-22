package com.scala.learn.basic.lectures.part2oop

import scala.annotation.tailrec

/* Created by imransarwar on 2020-01-11 */
object OOBasics extends App {
//  val person = new Person
//  println(person)
  val person = new Person("Ashtalfa", 8)
  //Note person.age won't resolve as age is class parameter but not class member
  //Class parameters are not fields and to covert them class member, put val or var
  //println(person.age)
  //After adding val in constructor
  println(person.age)
  println(person.x)

  person.greetings("Muhammad")
  person.greetings()

  val hussain = new Person()
  hussain.greetings()

  // ASSIGNMENT
  val imranWriter: Writer = new Writer("Umar", "Bin Imran", 1981)
  val fairyNovel: Novel = new Novel("Fairy Tales", 2010, imranWriter)
  println(s"Author Age: ${fairyNovel.authorAge()}")

  val counter = new Counter()
  counter.inc.print
  counter.inc.inc.print
  counter.inc(5).print


}

//class Person
//class Person(name: String, val age: Int)
class Person(name: String, val age: Int = 1) {
  val x:Int = 10
  //Print before x as class block is evaluated first
  println("Would print before x :D, side effect !")

  // Note name is not class member but it is accessible in class
  def greetings(name: String): Unit = println(s"${this.name} says hi to $name")

  //overloading, return type is not considered in overloading
  def greetings(): Unit = println(s"Hi $name: age:$age")

  //Multiple Constructors
  //Auxilary constructor
  //Now, this constructor is not required if we provide default value to class constructor
  //def this(name: String) = this(name, 1)
  def this() = this("Hussain")
}

// ASSIGNMENT
/*
Novel and Writer

Writer: first name, surname, year
- fullName

Novel: name, year of release, author
- authorAge
- isWrittenBy(author)
- copy(new year of release) = new instance of Novel
 */

class Writer(fName: String, lName: String, val year: Int) {
  def fullName(): String = {
    fName + lName
  }
}

class Novel(name: String, released: Int, author: Writer) {
  def authorAge(): Int = {
    released - author.year
  }

  def isWrittenBy(author: Writer): Boolean = {
    this.author == author
  }

  def copy(newYear: Int): Novel = {
    new Novel(this.name, newYear, this.author)
  }
}

/*
Counter class
- receives an int value
- method current count
- method to increment/decrment => new Counter
- overload method to increment/decrment with val => new Counter
 */

class Counter(val count: Int = 1) {
  //receives does the same if we do obj.count
  def inc: Counter = {
    println("incrementing...")
    new Counter(count + 1) // immutability
  }
  def dec: Counter = {
    println("decrementing...")
    new Counter(count - 1)
  }

  //We may use overloaded inc and dec using tail rec
//  def inc(incBy: Int) = new Counter(count + incBy)
//  def dec(decBy: Int) = new Counter(count - decBy)

  @tailrec
  final def inc(incBy: Int): Counter = {
    if(incBy <= 0) this
    else inc.inc(incBy-1)
  }
  @tailrec
  final def dec(decBy: Int): Counter = {
    if(decBy <= 0) this
    else dec.dec(decBy-1)
  }

  def print = println(this.count)
}


