package com.scala.learn.advance.lectures.part5ts

/* imransarwar created on 05/03/2020*/
object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics

  class Cage[T]
  // Qustion: Should a cage cat also kooran cord inherit from cage
  // One possible answer:
  // yes - covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat]

  // 2nd possible answer
  // no - invariance
  class ICage[T]
  //  val icage: ICage[Animal] = new ICage[Cat]
  //  val x: Int = "hello"

  // 3rd possible answer
  // hell no - opposite = contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal]

  // Covariance and Covariant Position
  class InvariantCage[T](val animal: T) // invariant

  // covariant positions
  class CovariantCage[+T](val animal: T) // COVARIANT POSITION

  // Contravariat type T occured in covariant position
  //  class ContravariantCage[-T](val animal: T)
  /*
    Why the above Contravariant never compiled, because we may provide wrong animal with the wrong cage
    val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)
   */

  //  class CovariantVariableCage[+T](var animal: T) // types of vars are in CONTRAVARIANT POSITION
  /*
    Suppose if above get compiled, so we may assign improper animal for cage also
    val ccage: CCage[Animal] = new CCage[Cat](new Cat)
    ccage.animal = new Crocodile
   */

  //  class ContravariantVariableCage[-T](var animal: T) // also in COVARIANT POSITION
  /*
    val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)
   */
  // NOTE: Only acceptable way for a var field is INVARIANT
  class InvariantVariableCage[T](var animal: T) // ok

//    trait AnotherCovariantCage[+T] {
//      def addAnimal(animal: T) // covariant type T occurs in CONTRAVARIANT POSITION
//    }
  /*
    Why the above method definition is not compiled
    Reason: we may create ccage of dog and add cat using function
    val ccage: CCage[Animal] = new CCage[Dog]
    ccage.add(new Cat)
   */

  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true
  }
  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  class Kitty extends Cat
  acc.addAnimal(new Kitty)

  class MyList[+A] {
    // def add(element: A): MyList[A]
    // Above method won't let us add elements like Cat and Dog in MyList[Kitty]
    // To fix the above problem, we may mention the super type
    // [B >: A] B is a super type of A
    // Here it is called WIDENING the Type and we achieved what was not compiling initially
    def add[B >: A](element: B): MyList[B] = new MyList[B] // widening the type
  }

  val emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty)
  // This line compiled successfully because of B >: A
  val moreAnimals = animals.add(new Cat)
  val evenMoreAnimals = moreAnimals.add(new Dog)

  // METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION.

  // return types
  class PetShop[-T] {
    //    def get(isItaPuppy: Boolean): T // METHOD RETURN TYPES ARE IN COVARIANT POSITION
    /*
      val catShop = new PetShop[Animal] {
        def get(isItaPuppy: Boolean): Animal = new Cat
      }

      val dogShop: PetShop[Dog] = catShop
      dogShop.get(true)   // EVIL CAT!
     */

    def get[S <: T](isItaPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
  //  val evilCat = shop.get(true, new Cat)
  class TerraNova extends Dog
  val bigFurry = shop.get(true, new TerraNova)

  /*
    Big rule
    - method arguments are in CONTRAVARIANT position
    - return types are in COVARIANT position
   */

  /**
   * Parking Application that checks for illegal parking
   * 1. Invariant, covariant, contravariant
   *   Parking[T](things: List[T]) {
   *     park(vehicle: T)
   *     impound(vehicles: List[T])
   *     checkVehicles(conditions: String): List[T]
   *   }
   *
   * 2. used someone else's API: IList[T]
   * 3. Parking = monad!
   *     - flatMap
   */
  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle
  class IList[T]

  // Invariant Implementation
  // This implementation is limited and it allows only one type
  class IParking[T](vehicles: List[T]) {
    def park(vehicle: T): IParking[T] = ???
    def impound(vehicles: List[T]): IParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  // Covariant Implementation
  // Considered better in case we expect collection of Vehicles(Things)
  class CParking[+T](vehicles: List[T]) {
    // def park(vehicle: T): CParking[T] = ???
    def park[S >: T](vehicle: S): CParking[S] = ???
    def impound[S >: T](vehicles: List[S]): CParking[S] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => CParking[S]): CParking[S] = ???
  }

  // Contravariant Implementation
  // Better to use when we consider group of actions
  class XParking[-T](vehicles: List[T]) {
    def park(vehicle: T): XParking[T] = ???
    def impound(vehicles: List[T]): XParking[T] = ???
    def checkVehicles[S <: T](conditions: String): List[S] = ???

    def flatMap[R <: T, S](f: Function1[R, XParking[S]]): XParking[S] = ???
  }

  /*
    Rule of thumb
    - use covariance = COLLECTION OF THINGS
    - use contravariance = GROUP OF ACTIONS
   */

  // Answer for Q.2
  // IParking would remain same

  class CParking2[+T](vehicles: IList[T]) {
    def park[S >: T](vehicle: S): CParking2[S] = ???
    // Covariant type T occurs in invariant position
    // Why the below function is not compiled, where IList was contravariant and is placed in function parameter
    // def impound(vehicles: IList[T]): CParking2[T] = ???
    // Reason: +T is covariant and still IList[T] is contravariant, compiler would complain
    def impound[S >: T](vehicles: IList[S]): CParking2[S] = ???
    // Covariant type T occurs in invariant position
    // It is because of IList[T]
    // def checkVehicles(conditions: String): IList[T] = ???
    def checkVehicles[S >: T](conditions: String): IList[S] = ???
  }

  class XParking2[-T](vehicles: IList[T]) {
    def park(vehicle: T): XParking2[T] = ???
    def impound[S <: T](vehicles: IList[S]): XParking2[S] = ???
    def checkVehicles[S <: T](conditions: String): IList[S] = ???
  }

  // flatMap
}
