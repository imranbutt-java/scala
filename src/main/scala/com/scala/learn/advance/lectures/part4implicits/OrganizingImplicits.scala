package com.scala.learn.advance.lectures.part4implicits

/* imransarwar created on 25/02/2020*/
object OrganizingImplicits extends App {
  // override def sorted[B >: A](implicit ord: Ordering[B]): C = super.sorted(ord)
  // So scala has some already implicit ordering value for int
  // Where does scala looks for this implicit ?
  // Yes, from scala.Predef
  // Now see above List would be printed in reverse order, but if the same reverseOrdering, we would
  // write after println it won't work
  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan( _ > _)
  // See the interesting thing
  // if val is changed to def, it would still work
  // implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan( _ > _)
  // but if we put () with the def, it won't work
  // implicit def reverseOrdering(): Ordering[Int] = Ordering.fromLessThan( _ > _)
  println(List(1,4,5,3,2).sorted)
//  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan( _ > _)

  /*
    Implicits (used as implicit parameters):
      - val/var
      - object
      - accessor methods = defs with no parentheses
   */

  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

  // Uncommenting below line, won't compile as compiler won't find sorting with Person
  // println(persons.sorted)

  // NOTE:
  // If alphabetical ordering and age ordering are likely to occur same time, then package them separately in
  // different objects, so we comment the below classes and made
  // AlphabeticNameOrdering and AgeOrdering
  //

  /*
   Best Practice:
   When defining an implicit
   1) If there is a single possible value for it, and you can edit the code for the type, then define the
      implicit in the companion object
   2) If you there are many possible values for implicit, but a single good one, and you can edit the code
      for the type, then define the good implicit in companion object
   */
  //  object Person {
  //    implicit val alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  //  }
  //  implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  //  println(persons.sorted)

  /*
    Implicit scope, here ordered by priority
    - normal scope = LOCAL SCOPE
    - imported scope
    - companions of all types involved in the method signature
      - List
      - Ordering
      - all the types involved = A or any supertype
   */
  // def sorted[B >: A](implicit ord: Ordering[B]): List[B]

  // implicit in companion object
  object AlphabeticNameOrdering {
    implicit val alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }

  import AgeOrdering._
  println(persons.sorted)

  /*
    Exercise.
    Say we have an app for sale purchase

    - totalPrice = most used (50% used by other programmers)
    - by unit count = 25%
    - by unit price = 25%

   */
  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }

  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }


}

