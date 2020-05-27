package com.scala.learn.interview.Lists

import scala.annotation.tailrec
import scala.util.Random

// Could be made trait too !!!
//trait RList[+T] {
sealed abstract class RList[+T] {
  def head: T
  def tail: RList[T]
  def isEmpty: Boolean
//   def headOption: Option[T]
  //The below function won't compile, bcz covariant is in Contravariant position, solution is with Subtype
//  def prepend(elem: T): RList[T]
  // By making prepend as :: we made it right associative
  def ::[S >: T](elem: S): RList[S] = new ::(elem, this)

  /**
   * Easy Problems
   */

  // Q: Find k-th element at index
  def apply(index: Int): T

  // Q: Find List Length
  def length: Int

  // Q: Reverse the List
  def reverse: RList[T]

  // Q: Concatenate list
  def ++[S >: T](otherList: RList[S]): RList[S]

  // Q: Remove and element
  def removeAt(index: Int): RList[T]

  // Q: Implement Map, FlatMap and Filter
  def map[S](f: T => S): RList[S]
  def flatMap[S](f: T => RList[S]): RList[S]
  def filter(f: T => Boolean): RList[T]

  /**
   * Medium Problems
   */
  // Run Length Encoding
  def rle: RList[(T, Int)]
  // Duplicate Each element, number of times in a Row
  def duplicateEach(times: Int): RList[T]
  // Rotate List
  def rotate(k: Int): RList[T]
  // Random samples
  def samples(k: Int): RList[T]

  /**
   * Hard Problems
   */
  // sorting using insertion sort
  // sorting the list in the order defined by the Ordering Object
  def insertionSort[S >: T](ordering: Ordering[S]): RList[S]
  def mergeSort[S >: T](ordering: Ordering[S]): RList[S]
  def quickSort[S >: T](ordering: Ordering[S]): RList[S]
}

case object RNil extends RList[Nothing] {
  // Note Throwing exception is a side effect with pure FP
  // For the sake of safety you may use Option as commented
  override def head: Nothing = throw new NoSuchElementException
  override def tail: RList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def toString: String = "[]"
//   override def headOption: Option[Nothing] = None
  // As prepend implementation is same, so prepend is defined in parent class
//  override def prepend[S >: Nothing](elem: S): RList[S] = Cons(elem, this)

  override def apply(index: Int): Nothing = throw new IndexOutOfBoundsException

  override def length: Int = 0

  override def reverse: RList[Nothing] = RNil

  override def ++[S >: Nothing](otherList: RList[S]): RList[S] = otherList

  override def removeAt(index: Int): RList[Nothing] = throw new NoSuchElementException

  override def map[S](f: Nothing => S): RList[S] = RNil
  override def flatMap[S](f: Nothing => RList[S]): RList[S] = RNil
  override def filter(f: Nothing => Boolean): RList[Nothing] = RNil

  /**
   * Medium
   */
  override def rle: RList[(Nothing, Int)] = RNil

  override def duplicateEach(times: Int): RList[Nothing] = RNil

  override def rotate(k: Int): RList[Nothing] = RNil

  override def samples(k: Int): RList[Nothing] = RNil

  /**
   * Hard Problems
   */
  override def insertionSort[S >: Nothing](ordering: Ordering[S]): RList[S] = RNil

  override def mergeSort[S >: Nothing](ordering: Ordering[S]): RList[S] = RNil

  override def quickSort[S >: Nothing](ordering: Ordering[S]): RList[S] = RNil
}

// Cons renamed to :: as acceptable name class in scala
case class ::[+T](override val head: T, override val tail: RList[T]) extends RList[T] {
  override def isEmpty: Boolean = false

  override def toString: String = {
    @tailrec
    def toStringTailRec(remaining: RList[T], result: String): String = {
      if(remaining.isEmpty) result
      else if(remaining.tail.isEmpty) s"$result${remaining.head}"
      else toStringTailRec(remaining.tail, s"$result${remaining.head}, ")
    }
    "["+ toStringTailRec(this, "") +"]"
  }

//  override def prepend[S >: T](elem: S): RList[S] = Cons(elem, this)

  override def apply(index: Int): T = {
    if(index < 0) throw new NoSuchElementException
    @tailrec
    def applyTailRec(remaining: RList[T], currentIndex: Int): T = {
      if(currentIndex == index) remaining.head
      else applyTailRec(remaining.tail, currentIndex + 1)
    }
    applyTailRec(this, 0)
  }

  override def length: Int = {
    @tailrec
    def lengthTailRec(remaining: RList[T], count: Int): Int = {
      if(remaining.isEmpty) count + 1
      else lengthTailRec(remaining.tail, count+1)
    }
    lengthTailRec(this, 0)
  }

  // We tried to create new list, concatenating with head
  override def reverse: RList[T] = {
    @tailrec
    def reverseTailRec(remaining: RList[T], result: RList[T]): RList[T] = {
      if(remaining.isEmpty) result
      else reverseTailRec(remaining.tail, remaining.head :: result)
    }
    reverseTailRec(this, RNil)
  }

  /**
   * It appends 2 RList
   * Complexity: O(M) + O(M + N)
   * N = Length of this List
   * M = Length of other List
   * @param otherList
   * @tparam S
   * @return
   */
  override def ++[S >: T](otherList: RList[S]): RList[S] = {

    // We may also give solution, but it would stack recursive, bcz ++ is evaluated first before prepend
    // head :: (tail ++ otherList)
    /*
    [1,2,3] ++ [4,5] = concatListTailRec([4,5], [3,2,1])
    = concatListTailRec([5], [4,3,2,1])
    = concatListTailRec([], [5,4,3,2,1])
    = [5,4,3,2,1]
    [5,4,3,2,1].reverse = [1,2,3,4,5]

    Complexity = O(N)
     */
    @tailrec
    def concatListTailRec(remaining: RList[S], acc: RList[S]): RList[S] = {
      if(remaining.isEmpty) acc
      else concatListTailRec(remaining.tail, remaining.head :: acc)
    }
    concatListTailRec(otherList, this.reverse).reverse
  }

  /*
  we may also implement below solution but it is stack recursive, bcz it evaluates this part tail.removeAt(index - 1)
  first and then prepend
  override def removeAt(index: Int): RList[T] = {
    if(index == 0) tail
    else head :: tail.removeAt(index - 1)
  }
  [1,2,3,5].removeAt(2) = removeAtTailRec([1,2,3,4,5], 0, [])
  = removeAtTailRec([2,3,4,5], 0, [1])
  = removeAtTailRec([3,4,5], 1, [2,1])
  = removeAtTailRec([4,5], 2, [2,1])
  = [2,1].reverse ++ [4,5]
  = [1,2] ++ [4,5] = [1,2,4,5]

  Complexity = O(N)
   */
  override def removeAt(index: Int): RList[T] = {
    if(index > this.length || index < 0) throw new IndexOutOfBoundsException

    @tailrec
    def removeAtTailRec(remaining: RList[T], newList: RList[T], currentInd: Int): RList[T] = {
      if(currentInd == index) newList.reverse ++ remaining.tail
      else removeAtTailRec(remaining.tail, remaining.head :: newList, currentInd + 1)
    }
    removeAtTailRec(this, RNil, 0)
  }

  /*
  The below implementation easy and pretty but it is stack recursive
  override def map[S](f: T => S): RList[S] = f(head) :: tail.map(f)

  [1,2,3].map(x => x + 1) = mapTailRec([1,2,3], [])
  = mapTailRec([2,3], [f(1)]) => mapTailRec([2,3], [2])
  = mapTailRec([3], [f(2), 2]) => mapTailRec([3], [3, 2])
  = mapTailRec([], [f(3), 3, 2]) => mapTailRec(4, 3, 2)
  = RList(4,3,2).reverse
  = RList(2,3,4)

  Complexity = O(N + O(f))
   */
  override def map[S](f: T => S): RList[S] = {
    @tailrec
    def mapTailRec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if(remaining.isEmpty) acc.reverse
      else mapTailRec(remaining.tail, f(remaining.head) :: acc)
    }
    mapTailRec(this, RNil)
  }

  /*
  Below is a good solution but would cause stack recursion
  f(head) ++ tail.flatMap(f)
  [1,2,3].flatMap(x => [x, x+1]) = flatMapTailRec([1,2,3], [])
  flatMapTailRec([2,3], f([1])) => flatMapTailRec([2,3], [1,2])
  flatMapTailRec([3], f(2) ++ [1,2]) => flatMapTailRec([3], [2,3] ++ [1,2])
  flatMapTailRec([], f(3) ++ [2,3] ++ [1,2]) => flatMapTailRec([], [3,4] ++ [2,3] ++ [1,2])
  [3,4,2,3,1,2].reverse => [2,1,3,2,4,3]
  But we need [1,2,2,3,3,4]

  To fix that we would reverse on f(remaining.head).reverse
  Complexity:
  O(sum of all the lengths of f(RList) = Z)
  implies that O(Z^2)

   */
  override def flatMap[S](f: T => RList[S]): RList[S] = {
    @tailrec
    def flatMapTailRec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if(remaining.isEmpty) acc.reverse
      else flatMapTailRec(remaining.tail, f(remaining.head).reverse ++ acc)
    }

    /*
    [1,2,3].betterFlatMap(x => [x, x*2]) = betterFlatMap([1,2,3], [])
    = betterFlatMap([2,3], [[2,1]]) //[1,2].reverse
    = betterFlatMap([3], [[4,2], [2,1]])
    = betterFlatMap([], [[6,4], [4,2], [2,1]])
    = concatAll([[6,4], [4,2], [2,1]], [], [])
    = concatAll([[4,2], [2,1]], [6,4], [])
    = concatAll([[4,2], [2,1]], [4], [6])
    = concatAll([[4,2], [2,1]], [], [4,6])
    = concatAll([[2,1]], [4,2], [4,6])
    = concatAll([[2,1]], [2], [4,4,6])
    = concatAll([[2,1]], [], [2, 4, 4, 6])
    = concatAll([[]], [2,1], [2, 4, 4, 6])
    = concatAll([[]], [1], [2, 2, 4, 4, 6])
    = concatAll([[]], [], [1, 2, 2, 4, 4, 6])
    = [1, 2, 2, 4, 4, 6]

    Complexity: O(N + Z)
     */
    @tailrec
    def betterFlatMap(remaining: RList[T], acc: RList[RList[S]]): RList[S] = {
      if(remaining.isEmpty) concatAll(acc, RNil, RNil)
      else betterFlatMap(remaining.tail, f(remaining.head).reverse :: acc)
    }

    /*
    Complexity: O(N)
     */
    @tailrec
    def concatAll(elements: RList[RList[S]], currList: RList[S], acc: RList[S]): RList[S] = {
      if(elements.isEmpty && currList.isEmpty) acc
      else if(currList.isEmpty) concatAll(elements.tail, elements.head, acc)
      else concatAll(elements, currList.tail, currList.head :: acc)
    }

//    flatMapTailRec(this, RNil)
    betterFlatMap(this, RNil)
  }

  // Complexity: O(N)
  override def filter(f: T => Boolean): RList[T] = {
    @tailrec
    def filterTailRec(remaining: RList[T], acc: RList[T]): RList[T] = {
      if(remaining.isEmpty) acc.reverse
      else if(f(remaining.head)) filterTailRec(remaining.tail, remaining.head :: acc)
      else filterTailRec(remaining.tail, acc)
    }
    filterTailRec(this, RNil)
  }

  /**
   * Medium
   */
  /*
  [1,2,2,1].rle = rleTailRec([2,2,1], [], (1,1)) // Goes to last line
  = rleTailRec([2,1], [(1,1)], (2,1)) // this call would go to 3rd if condition
  = rleTailRec([1], [(1,1)], (2,2)) // this call would go to the last condition
  = rleTailRec([], [(1,1), (2,1)], (1,1)) // this call would go to the 2nd condition
  = [(1,1), (2,1), (1,1)]  this would be sent back
   */
  override def rle: RList[(T, Int)] = {
    def rleTailRec(remaining: RList[T], acc: RList[(T, Int)], current: (T, Int)): RList[(T, Int)] = {
      if(remaining.isEmpty && current._2 == 0) acc
      else if(remaining.isEmpty) current :: acc
      else if(remaining.head == current._1) rleTailRec(remaining.tail, acc, current.copy(_2 = current._2 + 1))
      else rleTailRec(remaining.tail, current :: acc, (remaining.head, 1))
    }
    rleTailRec(tail, RNil, (head, 1)).reverse
  }

  /*
   Complexity O(N * times)
   */
  override def duplicateEach(times: Int): RList[T] = {
    def addXTimes(item: T, result: RList[T], times: Int): RList[T] = {
      if(times == 0) result
      else addXTimes(item, item :: result, times - 1)
    }
    def duplicateTailRec(remaining: RList[T], acc: RList[T]): RList[T] = {
      if(remaining.isEmpty) acc
      else duplicateTailRec(remaining.tail, acc ++ addXTimes(remaining.head, RNil, times))
    }
    duplicateTailRec(this, RNil)
  }

  /*
  [1,2,3].rotate(3) = [1,2,3]
  [1,2,3].rotate(6) = [1,2,3]
  [1,2,3].rotate(4) = [1,2,3].rotate(1)

  Complexity = O(max(K, Size of List))
   */
  override def rotate(k: Int): RList[T] = {
    def rotateTailRec(remaining: RList[T], rotationLeft: Int, buffer: RList[T]): RList[T] = {
      if(remaining.isEmpty && rotationLeft == 0) this
      else if(remaining.isEmpty && rotationLeft > 0) rotateTailRec(this, rotationLeft, RNil)
      else if(rotationLeft == 0) remaining ++ buffer.reverse
      else rotateTailRec(remaining.tail, rotationLeft - 1, remaining.head :: buffer)
    }
    rotateTailRec(this, k, RNil)
  }

  /*
  Complexity O(N * k)
   */
  override def samples(k: Int): RList[T] = {
    if(k <= 0) RNil
    val random = new Random(System.currentTimeMillis())
    val maxInd = this.length

    def samplesTailRec(leftElement: Int, acc: RList[T]): RList[T] = {
      if(leftElement == 0) acc
      else {
        val randomNum = random.nextInt(maxInd)
        samplesTailRec(leftElement - 1, this(randomNum) :: acc)
      }
    }
    def elegantSolution: RList[T] =
      RList.from((1 to k).map(_ => random.nextInt(maxInd)).map(index => this(index)))

//    samplesTailRec(k, RNil)
    elegantSolution
  }

  /**
   * Hard Problems
   */
  /*
  [2,1,3].sorted = insertSortTailrec([2,1,3], [])
  = insertSortTailrec([1,3], [2])
  = insertSortTailrec([3], [1,2])
  = insertSortTailrec([], [1,2,3])
  = [1,2,3]
  Complexity: O(N^2)
   */
  override def insertionSort[S >: T](ordering: Ordering[S]): RList[S] = {
    // Complexity: O(N^2)
    def insertSortTailrec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if(remaining.isEmpty) acc
      else insertSortTailrec(remaining.tail, insertSort(remaining.head, RNil, acc))
    }
    /*
    Means where that element should reside and before and after List are separated
    List(3,1,2,4).insertSort = insertSort(3, [], [1,2,4])
    = insertSort(3, [1], [2,4])
    = insertSort(3, [2, 1], [4])
    = [2,1].reverse + (3 :: [4])
    = [1,2] + [3,4]
    = [1,2,3,4]
    Complexity: O(N)
     */
    def insertSort(elem: T, before: RList[S], after: RList[S]): RList[S] = {
      if(after.isEmpty || ordering.lteq(elem, after.head)) before.reverse ++ (elem :: after)
      else insertSort(elem, after.head :: before, after.tail)
    }

    insertSortTailrec(this, RNil)
  }

  override def mergeSort[S >: T](ordering: Ordering[S]): RList[S] = {
    /*
     merge([1, 3], [2, 4, 5], [])
     = merge([3], [2, 4, 5], [1])
     = merge([3], [4, 5], [2, 1])
     = merge([], [4, 5], [3, 2, 1])
     = [3, 2, 1].reverse ++ [4, 5]
     = [1, 2, 3, 4, 5]
     */
    @tailrec
    def merge(listA: RList[S], listB: RList[S], acc: RList[S]): RList[S] = {
      if(listA.isEmpty) acc.reverse ++ listB
      else if(listB.isEmpty) acc.reverse ++ listA
      else if(ordering.lteq(listA.head, listB.head)) merge(listA.tail, listB, listA.head :: acc)
      else merge(listA, listB.tail, listB.head :: acc)
    }

    /*
    [3,1,2,5,4] => [[3], [1], [2], [5], [4]]

    = mst([[3], [1], [2], [5], [4]], [])  // Make smallList and keep bigList RList
    = mst([[2], [5], [4]], [[1, 3]])      // Applied merge on [[3], [1]]
    = mst([[4]], [[2, 5], [1, 3]])
    = mst([], [[4], [2, 5], [1, 3]])
    = mst([[4], [2, 5], [1, 3]], [])      // Swap bigList with smallList when smallList is empty
    = mst([[1, 3]], [[2, 4, 5]])          // Now again send first 2 elements and apply merge on them
    = mst([], [[1, 3], [2, 4, 5]])
    = mst([[1, 3], [2, 4, 5]], [])
    = mst([], [[1, 2, 3, 4, 5]]
    = [1, 2, 3, 4, 5]
     */
    @tailrec
    def mergeSortTailrec(smallList: RList[RList[S]], bigList: RList[RList[S]]): RList[S] = {
      if(smallList.isEmpty) {
        if(bigList.isEmpty) RNil
        else if(bigList.tail.isEmpty) bigList.head
        else mergeSortTailrec(bigList, RNil)
      } else if(smallList.tail.isEmpty) {
        if(bigList.isEmpty) smallList.head
        else mergeSortTailrec(smallList.head :: bigList, RNil)
      } else {
        val first = smallList.head
        val second = smallList.tail.head
        val merged = merge(first, second, RNil)
        mergeSortTailrec(smallList.tail.tail, merged :: bigList)
      }
    }

    mergeSortTailrec(this.map(x => x :: RNil), RNil)
  }

  /*
  Complexity: Worst O(N^2)
  Average O(N Log(N))
   */
  override def quickSort[S >: T](ordering: Ordering[S]): RList[S] = {
    /*
    partition([1,2,3,4,5], 3, [], []) => ([1,2,3], [4,5])
     */
    def partition(list: RList[T], pivot: T, smaller: RList[T], larger: RList[T]): (RList[T], RList[T]) = {
      if(list.isEmpty) (smaller, larger)
      else if(ordering.lteq(list.head, pivot)) partition(list.tail, pivot, list.head :: smaller, larger)
      else partition(list.tail, pivot, smaller, list.head :: larger)
    }

    /*
    [3,1,2,4,5].quickSort

    Expecting:
    -- partition([1,2,4,5], 3, [], []) => ([1,2], [4,5])
    -- partition([2], 1, [], []) => ([], [2])
    -- partition([[4], 5, [], []) => ([4], [])

    quickSortTailrec([3,1,2,5,4], [])                   // After partition we use the tuple as below
    = quickSortTailrec([[1,2], [3], [5,4]], [])         // remaining = [<smallList>, <pivot>, <bigList>]
    = quickSortTailrec([[], [1], [2], [3], [5,4]], []]  // Now we have empty/single list and we may put it in acc
    = quickSortTailrec([[1], [2], [3], [5,4]], [])
    = quickSortTailrec([[2], [3], [5,4]], [[1]])
    = quickSortTailrec([[3], [5,4]], [[2], [1]])
    = quickSortTailrec([[5,4]], [[3], [2], [1]])        // Here again call partition
    = quickSortTailrec([[4], [5], []], [[3], [2], [1]])
    = quickSortTailrec([[5], []], [[4], [3], [2], [1]])
    = quickSortTailrec([[]], [[5], [4], [3], [2], [1]])
    = quickSortTailrec([], [[5], [4], [3], [2], [1]])
    flat List
    [1,2,3,4,5]
     */
    def quickSortTailrec(remaining: RList[RList[T]], acc: RList[RList[T]]): RList[T] = {
      if(remaining.isEmpty) acc.flatMap(smallList => smallList).reverse
      else if(remaining.head.isEmpty) quickSortTailrec(remaining.tail, acc)
      else if(remaining.head.tail.isEmpty) quickSortTailrec(remaining.tail, remaining.head :: acc)
      else {
        val list = remaining.head
        val pivot = list.head
        val listToSplit = list.tail
        val (small, big) = partition(listToSplit, pivot, RNil, RNil)
        quickSortTailrec(small :: (pivot :: RNil) :: big :: remaining.tail, acc)
      }
    }

    quickSortTailrec(this :: RNil, RNil)
  }
}

// Creating companion object, to create RList
object RList {
  def from[T](iterable: Iterable[T]): RList[T] = {
    @tailrec
    def convertToRListTailRec(remaining: Iterable[T], acc: RList[T]): RList[T] = {
      if(remaining.isEmpty) acc
      else convertToRListTailRec(remaining.tail, remaining.head :: acc)
    }
    convertToRListTailRec(iterable, RNil).reverse
  }
}

object ListProblems extends App {

  RNil.:: (2) == 2 :: RNil // It gives good Syntactical Sugar

//  val aList = Cons(1, Cons(2, Cons(3, RNil)))
//  val aList = ::(1, ::(2, ::(3, RNil)))
  val aList = 1 :: 2 :: 3 :: RNil // RNil.::(3).::(2).::(1)
  println("# Find K-th Element")
  println(aList)
  println(aList(2))

  println("# Find Length of List")
  println(aList.length)

  println("# Reverse")
  println(aList.reverse)

  println("# Range")
  println(RList.from(1 to 10))

  println("# Concatenate")
  println(aList ++ (4 :: 5 :: RNil))

  println("# Remove At")
  println(RList.from(1 to 5).removeAt(2))

  println("# Map Function")
  println(RList.from(1 to 5).map(x => x + 1))

  println("# FlatMap Function")
  val largeList = RList.from(1 to 10000)
  val startAt = System.currentTimeMillis()
  println(largeList.flatMap(x => x :: (x + 1) :: RNil))
  println(s"Time Spent: ${System.currentTimeMillis() - startAt}")

  println("# Flat Map Better Performance")
  println((1 :: 2 :: 4 :: RNil).flatMap(x => x :: (x * 2) :: RNil))

  println("# filter Function")
  println(RList.from(1 to 5).filter(x => x % 2 == 0))

  println("# RLE")
  println((1 :: 2 :: 2 :: 1 :: RNil).rle)
  println((1 :: 1 :: 2 :: 2 :: RNil).rle)

  println("# Duplicate Each Element N Times")
  println((1 :: 2 :: 3 :: RNil).duplicateEach(3))

  println("# Rotate List by index")
  for {
    i <- 1 to 10
  } println(RList.from(1 to 5).rotate(i))

  println("# Find Random samples")
  println(RList.from(1 to 5).samples(3))

  println("###### Hard Problems ########")
  println("# Inserting Sort")
  println((3 :: 4 :: 2 :: 1 :: RNil).insertionSort(Ordering.fromLessThan[Int](_ < _)))

  println("# Merge Sort")
  println((3 :: RNil).mergeSort(Ordering.fromLessThan[Int](_ < _)))
  println((3 :: 4 :: 2 :: 1 :: RNil).mergeSort(Ordering.fromLessThan[Int](_ < _)))

  println("# Quick Sort")
  println((3 :: RNil).quickSort(Ordering.fromLessThan[Int](_ < _)))
  println((3 :: 4 :: 2 :: 1 :: RNil).quickSort(Ordering.fromLessThan[Int](_ < _)))
}
