package com.scala.learn.basic.lectures.part2oop

/* Created by imransarwar on 2020-01-12 */
object CaseClasses extends App {
  //Short hand classes to better make companion classes in one go.
  //Otherwise for every class we need to mention
  //hashcode, toString, equals etc. boilerplate code everytime

  case class Person(name: String, age: Int)

  // 1. Class Parameters are fields
  val girl = new Person("Ashtalfa", 8)
  println(girl.name)

  // 2. Sensible tostring
  println(girl)

  // 3. equals and hashcode implementation
  val girl2 = new Person("Ashtalfa", 8)
  //If 'case' is not used scala compiler would consider them different
  println(girl == girl2)

  // 4. Already implemented copy method
  val girl3 = girl.copy()
  println(girl == girl3)

  // 5. case classes have companion objects
  val thePerson = Person
  //Person(...) here calls apply method of companion object, So in case class instantiation
  // we may omit 'new' keyword
  val marry = Person("Mary", 23)

  // 6. Case classes are serializable
  // Mostly used by Akka framework, that securely send/receive files in distributed environment

  // 7. Case Classes have extractor patterns = CCs can be used in PATTERN MATCHING

  //THERE ARE SOME CASE OBJECTS
  //It has all above properties except companion objects as they are themselves
  case object Pakistan {
    def name: String = "Islamic Republic of Pakistan"
  }
}
