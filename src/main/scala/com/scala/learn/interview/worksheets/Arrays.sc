
val arr = Array(1,5,3,4,7)
arr.scanLeft(2)(_ min _)
arr.slice(2,3).scanLeft(Int.MaxValue)(_ min _).min

val interval = Array[Array[Int]]()
interval.foldLeft(List[Array[Int]]()) {
  case(x :: tail, y) if x(1) >= y(0) =>
    Array(x(0), Math.max(x(1), y(1))) :: tail
  case (tail, y) =>
    y :: tail
}.toArray

val matrix = Array[Array[Int]]()
val res = matrix
  .map(arr => arr(0))
  .sortWith(_ < _)
  .foldLeft((0, Option[Int](0))) {(tup, value) =>
    (Math.max(tup._1, tup._2.map(x => value - x).get), Option(value))
  }._1

('a' to 'z').toArray

5 % 5