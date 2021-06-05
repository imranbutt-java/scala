object sums {
  sealed trait Fruit
  object Fruit {
    case object Apple  extends Fruit
    case object Grapes extends Fruit
  }

  sealed trait Apple
  object Apple {
    case object GrannySmith extends Apple
    case object Fuji        extends Apple
  }

  sealed trait Grapes
  object Grapes {
    case object CottonCandy extends Grapes
    case object Concord     extends Grapes
    case object MoonDrops   extends Grapes
  }

  sealed case class WitnessStatement(statement: Statement, statementType: StatementType)
  sealed case class Statement(value: String) extends AnyVal

  sealed trait StatementType
  case object StatementType {
    case object Truth extends StatementType
    case object Lie   extends StatementType
  }
}

object product {
  sealed trait Fruit
  sealed trait Apple extends Fruit
  object Apple {
    case object GrannySmith extends Apple
    case object Fuji        extends Apple
  }

  sealed trait Grapes extends Fruit
  object Grapes {
    case object CottonCandy extends Grapes
    case object Concord     extends Grapes
    case object MoonDrops   extends Grapes
  }

  /*
  - ADTs structure domain model
  |••| + |••| = |••| + |••••|  = |••••|  OR |••|    OR |••••|
                |••|             |••|       |••••|       |••|

  - Composable : input1 > Function1 > output1 > Function2 > output2
  - It saves us from introducing unknown state
  - ADTs are data container
  - sum and product are associated with the number of instances possible for a particular type.

   */

  import Apple._
  import Grapes._

  // Product : Number of possible instances of the type
  case class FruitSalad(c: Apple, p: Grapes)
  FruitSalad(GrannySmith, CottonCandy)
  FruitSalad(GrannySmith, Concord)
  FruitSalad(GrannySmith, MoonDrops)
  FruitSalad(Fuji, CottonCandy)
  FruitSalad(Fuji, Concord)
  FruitSalad(Fuji, MoonDrops)

  sealed trait Day
  object Day {
    case object Morning   extends Day
    case object Noon      extends Day
    case object Afternoon extends Day
    case object Evening   extends Day
    case object Night     extends Day
  }

  /*
  - Number of possible instances of a type
  Apple  = A | B
  Grapes = X | Y | Z




  possible of FruitSalad boxes = 2 x 3
   */
  /*
  name = N
  age  = 1 - 130
  No of possible instance = size.of.name * size.of.age

  --
  Mandatory part of any entity
   */
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 33)
  val dd  = true
  dd isInstanceOf Boolean

  type One = Unit
  val one = ()

  sealed trait Cup
  object Cup {
    case object A extends Cup
    case object B extends Cup
  }

  sealed trait Plate
  object Plate {
    case object X extends Plate
    case object Y extends Plate
    case object Z extends Plate
  }
  case class ProductWithUnity(c: Cup, p: Plate, anythingElse: Unit)
  /*
  size of x = 2
  size of y = 2
  size of z = 1
  size of Point =  2 * 2 * 1
   */
  import Cup._
  import Plate._

  val a = ProductWithUnity(A, X, bob)
  val b = ProductWithUnity(A, Y, "String")
  val c = ProductWithUnity(A, Z, 10)
  val d = ProductWithUnity(B, X, 3.0)
  val e = ProductWithUnity(B, Y, true)
  val f = ProductWithUnity(B, Z, ())

  val testSetCarton = List(a, b, c, d, e, f)
  testSetCarton foreach { p =>
    p match {
      case ProductWithUnity(e, f, g) => println(s"($e,  $f, $g)")
    }
  }

  ProductWithUnity(A, Y, "String") == ProductWithUnity(A, Y, println(4000))
}
