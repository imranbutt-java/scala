package com.scala.learn.advance.lectures.part5ts

/* imransarwar created on 08/03/2020*/
object PathDependantTypes extends App {

  // path-dependent types
  class Outer {
    class Inner
    object InnerObject
    // Type Members could only defined in class and traits rest we may define Type Aliases
    type InnerType

    def print(i: Inner) = println(i)
    def printGeneral(i: Outer#Inner) = println(i)
  }

  def aMethod: Int = {
    class HelperClass
    // So here compiler is expecting to provide some alias, like we provided as String
    type HelperType = String
    2
  }

  // per-instance
  val o = new Outer
  val inner = new o.Inner // o.Inner is a TYPE

  val oo = new Outer
  // This won't compile, bcz oo.Inner is a different TYPE than o.Inner
  //  val otherInner: oo.Inner = new o.Inner
  o.print(inner)
  //  oo.print(inner)

  // But here path dependant types are have one same parent, Outer#Inner
  o.printGeneral(inner)
  oo.printGeneral(inner)

  // Lesson: When we are not sure, if the inner instance passed  created by any instance, than a better option
  // to accept the type with path-dependant types

  /*
    Exercise
    DB keyed by Int or String, but maybe others in future

    trait Item[Key]
    trait IntItem extends Item[Int]
    trait StringItem extends Item[String]

    def get[ItemType](key: Key): ItemType

    So we may call function get like this
    get[IntItem](2) // OK
    get[StringItem]("Hi") // OK

    get[IntItem]("Hello") // is not ok
   */

  /*
    use path-dependent types
    abstract type members and/or type aliases
   */

  trait ItemLike {
    type Key
  }

  trait Item[K] extends ItemLike {
    type Key = K
  }

  trait IntItem extends Item[Int]
  trait StringItem extends Item[String]

  def get[ItemType <: ItemLike](key: ItemType#Key): ItemType = ???

  get[IntItem](42) // ok
  get[StringItem]("home") // ok

  // get[IntItem]("scala") // not ok
}
