package com.scala.learn.interview.array

/* imransarwar created on 19/09/2020*/
object Pattern132 extends App {
  def find132pattern(nums: Array[Int]): Boolean = {
    val n = nums.length
    var right = n
    var rightNum = Int.MinValue

    for (left <- n - 1 to 0 by -1) {
      // Assumes that rightNum is already smaller than some element nums[mid] where mid is between left and rightNum's index
      // We find a "132" pattern if nums[left] < rightNum
      if(nums(left) < rightNum) return true

      // Now we have nums[left] >= rightNum

      // We now view nums[left] as the mid element, and increase rightNum as much as possible, but keep rightNum < nums[left].
      // We do this because we want to maximize the chance of finding a "132" pattern in a future iteration.
      // Note that {nums[right], ..., nums[n-1]} is a stack has the following property:
      // nums[right] <= nums[right+1] <= ... <= nums[n-1]
      val mid = left
      while (right < n && nums(mid) > nums(right)) {
        rightNum = nums(right)
        right += 1
      }
      // Now we have nums[left] <= nums[right] (which indicates that the stack is monotonical)
      // We push nums[left] to the "stack"
      right -= 1
      nums(right) = nums(mid)
    }
    false
  }
  find132pattern(Array(3,1,4,2))
}
