package com.scala.learn.interview.array

/* imransarwar created on 30/11/2020*/
object SortColors extends App {
  def sortColors(nums: Array[Int]): Unit = {
    //Sorting.quickSort(nums)

    def swap(s: Int, d: Int) = {
      val tmp = nums(d)
      nums(d) = nums(s)
      nums(s) = tmp
    }

    //Dutch National Flag Algorithm OR 3-way Partitioning
    nums.indices.foldLeft(0, 0, nums.size - 1) {
      case ((lo, mid, hi), _) if (mid <= hi) => {
        println(s"$lo, $mid, $hi")
        nums(mid) match {
          case 0 =>
            // Swap lo and mid value and increment lo and mid pointers.
            swap(lo, mid)
            (lo + 1, mid + 1, hi)
          case 1 =>
            // Increment just mid pointer
            (lo, mid + 1, hi)
          case 2 =>
            // Swap mid and hi values, and decrement hi pointer
            swap(mid, hi)
            (lo, mid, hi - 1)
        }
      }
    }
  }

  val arr = Array(0,1,2,2,0,1)
  sortColors(arr)
  arr.foreach(print)
}
