def stopRepeats[T](str: LazyList[T]): LazyList[T] = {
  val halfSpeed = str.flatMap(x => Seq(x, x))
  val result = halfSpeed.zip(str) // Stream[(T, T)]
    .drop(1) // Enforce the condition k > 0.
    .takeWhile { case (h, s) => h != s } // Stream[(T, T)]
    .map(_._2) // Stream[T]
  str.head +: result // Prepend the first element that was dropped.
}

stopRepeats(LazyList(1, 3, 5, 7, 9, 3, 5, 7, 9)).toList

// Iterators
(1 to 10).view.flatMap(x => 1 to 300_000_000).max