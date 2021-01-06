import scala.collection.mutable._

val stack = Stack[Int]()
val list = ListBuffer[Int]()

val n = 352
val nums = n.toString.toArray
nums.splitAt(2)._2.toString.reverse

val arr = Array("ab", "cb", "de", "fg")
arr.zip(arr.drop(0))
arr.zip(arr.drop(1))
arr.zip(arr.drop(2))

"ABC".substring(2)

val inorder = Array(1,2,4,5)
val preorder = Array(2, 1, 4, 5)

inorder.indexWhere(_ == preorder(0))


def keys(ch: Char): Int = ch match {
  case 'A' => 2
  case 'B' => 4
  case 'C' => 3
}

val letters = "ABC"
val comb = List("")
comb.flatMap(x => keys(letters.head).toString.map(y => x + y))


val numbers = List(1,2,3,4)
val chars = List('a','b','c','d')
val combination = numbers.flatMap(x => chars.map(y => x+""+y))
val combination2 = for {
  x <- numbers
  y <- chars
} yield x +""+ y

val arr2 = Array(1, 3, 4)

import scala.collection.mutable
val map = mutable.Map[Char, Int]()
map.addOne('A', 1)
map.addOne('A', map('A') + 1)
map

val arr3 = Array(0, 1, 0, 2, 2, 1, 0)
arr3.indices.foldLeft(0)((initial, values) => initial + initial)
arr3.indices.foldLeft(0)((initial, preRes) => preRes + preRes)
arr3.indices.foldLeft("")((initial, preRes) =>  {
  println(initial + initial)
  initial + initial
})
arr3.indices.foldLeft("")((initial, preRes) =>  {
  println(preRes)
  preRes +""+ initial
})
