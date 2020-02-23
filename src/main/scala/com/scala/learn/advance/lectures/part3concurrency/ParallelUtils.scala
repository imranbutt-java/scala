package com.scala.learn.advance.lectures.part3concurrency

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.atomic.AtomicReference

import scala.collection.parallel.{ForkJoinTaskSupport, Task, TaskSupport}
import scala.collection.parallel.immutable.ParVector
import scala.collection.parallel.CollectionConverters._

/* imransarwar created on 23/02/2020*/
object ParallelUtils extends App {
  // 1. Parallel Collection

  // Operation performed by multiple threads at the same time
  val parList = List(1, 2, 3).par
  // Parallel Vectore explicitly defining
  val aParVector = ParVector[Int](1,2,3)

  /*
    Seq
    Vector
    Array
    Map - Hash, Trie
    Set - Hash, Trie
   */

  def measure[T](operation: => T): Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }

  // When List contains less numbers like 10,000
  // Serial time would be less than parallel like Serial: 4 and Parallel: 70
  // If number: 10,000,000,000 than parallel would perform pretty better
  // Parallel Collection works on Map Reduce Model
  val list = (1 to 10000).toList
  val serialTime = measure {
    list.map(_ + 1)
  }
  println("serial time: " + serialTime)

  val parallelTime = measure {
    list.par.map(_ + 1)
  }

  println("parallel time: " + parallelTime)

  /*
    Map-reduce model
    - split the elements into chunks - Splitter
    - operation
    - recombine - Combiner
   */

  // Pretty safe operation when used parallel, they operate sequentially
  // map, flatMap, filter, foreach,
  //
  // Not sometimes safe, because, we don't know the order operands would be called.
  // reduce, fold

  // fold, reduce with non-associative operators
  println(List(1,2,3).reduce(_ - _))
  println(List(1,2,3).par.reduce(_ - _))

  // Problem may be faced with parallelism
  // Expected sum = 15 and actual result would be interested
  // synchronization sometimes required on the results
  var sum = 0
  List(1,1,1,1,2,2,2,2,2).par.foreach(sum += _)
  println(sum)  // race conditions!

  //Configuration

  aParVector.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(2))
  /*
    alternatives
    - ThreadPoolTaskSupport - deprecated
    - ExecutionContextTaskSupport(EC)
   */

  //  aParVector.tasksupport = new TaskSupport {
  //    override def execute[R, Tp](fjtask: Task[R, Tp]): () => R = ???
  //
  //    override def executeAndWaitResult[R, Tp](task: Task[R, Tp]): R = ???
  //
  //    override def parallelismLevel: Int = ???
  //
  //    override val environment: AnyRef = ???
  //  }

  // 2 - atomic ops and references

  val atomic = new AtomicReference[Int](2)

  val currentValue = atomic.get() // thread-safe read
  atomic.set(4) // thread-safe write

  atomic.getAndSet(5) // thread safe combo

  atomic.compareAndSet(38, 56)
  // if the value is 38, then set to 56
  // compare here does shallow equalities, means reference equality not deep equalities

  atomic.updateAndGet(_ + 1) // thread-safe function run
  atomic.getAndUpdate(_ + 1)

  atomic.accumulateAndGet(12, _ + _) // thread-safe accumulation
  atomic.getAndAccumulate(12, _ + _)
}