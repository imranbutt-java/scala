import scala.collection.mutable

var a = List(1)
a = a :+ 2
a

"abba".groupBy(identity).mapValues(_.size).filter(_._2 % 2 == 1).size <= 1
