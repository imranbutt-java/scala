package com.scala.learn.advance.lectures.part5ts

/* imransarwar created on 14/03/2020*/
object FBoundedPolymorphism extends App {
  // In class hirarchy design problem, how can we make method in super type to accept the current type
  //  trait Animal {
  //    def breed: List[Animal]
  //  }
  //
  //  class Cat extends Animal {
  //    override def breed: List[Animal] = ??? // List[Cat] !!
  //  }
  //
  //  class Dog extends Animal {
  //    override def breed: List[Animal] = ??? // List[Dog] !! <<< How to force compiler this to do for us
  //  }

  // Solution 1 - naive

  //  trait Animal {
  //    def breed: List[Animal]
  //  }
  //
  //  class Cat extends Animal {
  //    override def breed: List[Cat] = ??? // List[Cat] !!
  //  }
  //
  //  class Dog extends Animal {
  //    override def breed: List[Cat] = ??? // List[Dog] !! <<< Here List[Dog] covariant so me do the mistake
  //  }


  // Solution 2 - FBP

  //  trait Animal[A <: Animal[A]] { // recursive type: F-Bounded Polymorphism
  //    def breed: List[Animal[A]]
  //  }
  //
  //  class Cat extends Animal[Cat] {
  //    override def breed: List[Animal[Cat]] = ??? // List[Cat] !!
  //  }
  //
  //  class Dog extends Animal[Dog] {
  //    override def breed: List[Animal[Dog]] = ??? // List[Dog] !!
  //  }
  //
  //  trait Entity[E <: Entity[E]] // ORM
  //  class Person extends Comparable[Person] { // FBP
  //    override def compareTo(o: Person): Int = ???
  //  }
  //
  //  class Crocodile extends Animal[Dog] {
  // <<< Here even using F-Bounded Polymorphism, we may still do the mistake
  // Now how can we enforce the compiler, that return should be same list type of class
  //    override def breed: List[Animal[Dog]] = ??? // List[Dog] !!
  //  }

  // Solution 3 - FBP + self-types

  //  trait Animal[A <: Animal[A]] { self: A =>
  //      def breed: List[Animal[A]]
  //    }
  //
  //    class Cat extends Animal[Cat] {
  //      override def breed: List[Animal[Cat]] = ??? // List[Cat] !!
  //    }
  //
  //    class Dog extends Animal[Dog] {
  //      override def breed: List[Animal[Dog]] = ??? // List[Dog] !!
  //    }

  //  class Crocodile extends Animal[Dog] {
  //    override def breed: List[Animal[Dog]] = ??? // List[Dog] !!

  //  }
  //
  //  trait Fish extends Animal[Fish]
  //  class Shark extends Fish {
  // Even after FBP + self Type, we may get the below issue, lets see next solution
  //    override def breed: List[Animal[Fish]] = List(new Cod) // wrong
  //  }
  //
  //  class Cod extends Fish {
  //    override def breed: List[Animal[Fish]] = ???
  //  }

  // Exercise

  // Solution 4 type classes!

  // <<< Here CanBreed has a single method and we have split companion object etc
  //  trait Animal
  // First Step: Define Type classes method
  //  trait CanBreed[A] {
  //    def breed(a: A): List[A]
  //  }
  //
  //  class Dog extends Animal
  //  object Dog {
  // Second Step: Defined type class instances as implicit
  //    implicit object DogsCanBreed extends CanBreed[Dog] {
  //      def breed(a: Dog): List[Dog] = List()
  //    }
  //  }
  //
  // Defining something that may cast our class like Dog and have access to type class instance, where it has
  // method breed + accepts the implicit type class
  //  implicit class CanBreedOps[A](animal: A) {
  //    def breed(implicit canBreed: CanBreed[A]): List[A] =
  //      canBreed.breed(animal)
  //  }
  //
  //  val dog = new Dog
  //  dog.breed // List[Dog]!!
  //  /*
  //    new CanBreedOps[Dog](dog).breed(Dog.DogsCanBreed)
  //    implicit value to pass to breed: Dog.DogsCanBreed
  //   */
  //
  //  class Cat extends Animal
  //  object Cat {
  //    implicit object CatsCanBreed extends CanBreed[Dog] {
  //      def breed(a: Dog): List[Dog] = List()
  //    }
  //  }
  //
  //  val cat = new Cat
  //  cat.breed

  // Solution #5

  trait Animal[A] { // pure type classes
    def breed(a: A): List[A]
  }

  class Dog
  object Dog {
    implicit object DogAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }

  class Cat
  object Cat {
    implicit object CatAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }

  implicit class AnimalOps[A](animal: A) {
    def breed(implicit animalTypeClassInstance: Animal[A]): List[A] =
      animalTypeClassInstance.breed(animal)
  }

  val dog = new Dog
  dog.breed

  //  val cat = new Cat
  //  cat.breed

}
