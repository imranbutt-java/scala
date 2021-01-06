import scala.collection.mutable._

val maxHeap = PriorityQueue[Int]()(Ordering.by(identity))
List(1, 10, 4, 3, 3).foreach(maxHeap.enqueue(_))
maxHeap
maxHeap.filter(_ == 3).dequeue
while(maxHeap.nonEmpty) println(maxHeap.dequeue)

val maxHeap2 = PriorityQueue[(Int, Int)]()(Ordering.by(_._2))
List((1,2), (2,10), (3,4), (4, 1)).foreach(maxHeap2.enqueue(_))
maxHeap2
while(maxHeap2.nonEmpty) println(maxHeap2.dequeue)

val minHeap = PriorityQueue[Int]()(Ordering.by(identity)).reverse
val minHeap2 = PriorityQueue[Int]()(Ordering.by(-_))
List(1, 10, 4, 3).foreach(minHeap.enqueue(_))
List(1, 10, 4, 3).foreach(minHeap2.enqueue(_))
minHeap
minHeap2
while(minHeap.nonEmpty) println(minHeap.dequeue)
while(minHeap2.nonEmpty) println(minHeap2.dequeue)

val pair = List(1,3,2,1)
val minHeap3 = PriorityQueue[Int]()(Ordering.fromLessThan(pair(_) > pair(_)))
for(i <- 0 until pair.length) minHeap3.enqueue(i)
while(minHeap3.nonEmpty) println(minHeap3.dequeue)
val a = "abc"
a.substring(0, 1) + a(1).toUpper + a.substring(1+1, a.size)

val map = Map('a' -> 2, 'b' -> 2, 'c' -> 2)
val pq = PriorityQueue[Char]()(Ordering.fromLessThan[Char]((x, y) => if(map(x) == map(y)) map(x) > map(y) else map(x) < map(y)))
for(i <- "abcabc") pq.enqueue(i)
while(pq.nonEmpty) println(pq.dequeue)

val tupleArray = Array(('A', 3), ('B', 1), ('C', 2))
val tuplePQ = PriorityQueue[(Char, Int)]()(Ordering.fromLessThan((x, y) => x._2 < y._2))
tuplePQ.addAll(tupleArray)
while(tuplePQ.nonEmpty) println(tuplePQ.dequeue)


val multiCondPQ: PriorityQueue[(Int, Int, Int)] = PriorityQueue[(Int, Int, Int)]()(
  Ordering.fromLessThan((item1, item2) =>   {
    if (item1._2 < item2._2) true
    else if (item1._3 < item2._3) true
    else false
  })
)