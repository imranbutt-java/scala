import scala.collection.mutable

case class P(name: String, age: Int)

val list = mutable.ListBuffer[P]()
val p1 = P("One", 1)
val p2 = P("Two", 2)
val p3 = P("Three", 3)
list.addOne(p2).addOne(p1).addOne(p3)

list.sortBy(_.age)
list

List("rust", "java").foldLeft(List[Int]())((list, x) =>  list ::: List(x.length))