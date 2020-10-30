import scala.collection.immutable

val s = "abcabcc"
println("# String char count to map")
val m = s.view.groupBy(c => c).view.mapValues(str => str.size).toMap[Char, Int]
println(m)

object Solution extends App {
  def wordBreak(s: String, wordDict: List[String]): Boolean = {
    def wordBreak(str: String, wD: Set[String], start: Int): Boolean = {
      if (start == s.length) true
      else {
        for (end <- start + 1 until s.length)
          if (wD.contains(str.substring(start, end)) && wordBreak(s, wD, end)) true
        false
      }
    }
    wordBreak(s, immutable.HashSet(wordDict), 0)
  }

  println(wordBreak("Leetcode", List("Leet", "code")))
}