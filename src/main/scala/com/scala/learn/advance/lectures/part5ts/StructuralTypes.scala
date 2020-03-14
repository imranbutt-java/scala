package com.scala.learn.advance.lectures.part5ts

/* imransarwar created on 11/03/2020*/
object StructuralTypes extends App {
  // Structural Types: Where types are considered as a structure
  // Say, someone has created Java Closeable or Google Guava
  type JavaCloseable = java.io.Closeable

  // Both JavaCloseable and HipsterCloseable have close method which return Unit
  class HipsterCloseable {
    def close(): Unit = println("yeah yeah I'm closing")
    def closeSilently(): Unit = println("not making a sound")
  }

  // We are looking a method that may handle close funtion from either class and we need sth similar below method
  //  def closeQuietly(closeable: JavaCloseable OR HipsterCloseable) // ?!

  type UnifiedCloseable = {
    def close(): Unit
  } // STRUCTURAL TYPE

  def closeQuietly(unifiedCloseable: UnifiedCloseable): Unit = unifiedCloseable.close()

  // Now both classes may be passed in closeQuietly
  closeQuietly(new JavaCloseable {
    override def close(): Unit = ???
  })
  closeQuietly(new HipsterCloseable)



  // TYPE REFINEMENTS, so we have some types already and using structural types we may refine types


  type AdvancedCloseable = JavaCloseable {
    def closeSilently(): Unit
  }

  class AdvancedJavaCloseable extends JavaCloseable {
    override def close(): Unit = println("Java closes")
    def closeSilently(): Unit = println("Java closes silently")
  }

  def closeShh(advCloseable: AdvancedCloseable): Unit = advCloseable.closeSilently()

  closeShh(new AdvancedJavaCloseable)
  // Evne HipsterCloseable has closeSilently function, but it is not with the same root that's why
  // Type Refinement won't work for it, bcz compiler knows from where the type refinment originated
  // closeShh(new HipsterCloseable)

  // using structural types as standalone types
  // See close() func doc: Advanced language feature: reflective call
  def altClose(closeable: { def close(): Unit }): Unit = closeable.close()

  // type-checking => duck typing
  // Duck Typing is basically is used in Dynamic languages
  type SoundMaker = {
    def makeSound(): Unit
  }

  class Dog {
    def makeSound(): Unit = println("bark!")
  }

  class Car {
    def makeSound(): Unit = println("vrooom!")
  }

  val dog: SoundMaker = new Dog
  val car: SoundMaker = new Car

  // static duck typing
  // CAVEAT: based on reflection
  // So due to performance issue, only use Structural Types when we have no other option

  /*
    Exercises
   */

  // 1.
  trait CBL[+T]  {
    def head: T
    def tail: CBL[T]
  }

  class Human {
    def head: Brain = new Brain
  }

  class Brain {
    override def toString: String = "BRAINZ!"
  }

  def f[T](somethingWithAHead: { def head: T }): Unit = println(somethingWithAHead.head)

  /*
    f is compatible with a CBL and with a Human? Yes.
   */

  case object CBNil extends CBL[Nothing] {
    def head: Nothing = ???
    def tail: CBL[Nothing] = ???
  }
  case class CBCons[T](override val head: T, override val tail: CBL[T]) extends CBL[T]

  f(CBCons(2, CBNil))
  f(new  Human) // What is type parameter [T] = Compiler found it [Brain] !! **Interesting**

  // 2.
  object HeadEqualizer {
    type Headable[T] = { def head: T }

    def ===[T](a: Headable[T], b: Headable[T]): Boolean = a.head == b.head
  }

  /*
    is compatible with a CBL and with a Human? Yes.
   */
  // Here CBCons work for both as Brain and CBNil have head method
  val brainzList = CBCons(new Brain, CBNil)
  val stringsList = CBCons("Brainz", CBNil)

  HeadEqualizer.===(brainzList, new Human)
  // problem:
  // head for Human would return Brain and
  // head for stringsList would return String "Brainz", it is not type safe
  // Note in def === method, a.head == b.head is using reflection and it removes Type Specifier and
  // both Human and strinsList are Headable
  HeadEqualizer.===(new Human, stringsList) // not type safe

}