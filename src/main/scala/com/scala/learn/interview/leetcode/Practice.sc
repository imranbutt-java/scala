import scala.collection.mutable

val a = Array.ofDim[Int](3)
val b = Array.fill(3)(-1)
b(1) += 1
b.foreach(println)
a(1) += 1
a
a.filter(_ == 0).length == 2

val x = Array((3,0), (1,2), (1,1))
x.sortBy(t => (t._1, t._2)).foreach(println)

val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by{t: (Int, Int, Int) => (t._1, t._2, t._3)}).reverse
pq.enqueue((10, 2, 1), (2, 10, 1), (2, 1, 2))

while(!pq.isEmpty) println(pq.dequeue)

val nums = Array(1,3, 4, 5, 5, 0)
val maxNum = nums.reduce(_ max _)

