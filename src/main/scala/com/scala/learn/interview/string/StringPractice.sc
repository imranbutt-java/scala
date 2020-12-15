import scala.collection.mutable
import scala.collection.mutable.StringBuilder

def balancedString(str: String): String = {
  var slow = 0
  var tmp = new StringBuilder(str.substring(0, 1))
  def isBalanced(s: String): Boolean = {
    //      str.filter(_.isLower).toSeq.map(_.toUpper).contains(str.filter(_.isUpper))
    if(s.length == 0) return false
    val chars = Array.ofDim[Int](26)
    for(ch <- s.filter(_.isLower).toSet.mkString) {
      println(ch - 'a')
      chars(ch - 'a') += 1
    }
    for(ch <- s.filter(_.isUpper).toSet.mkString) {
      chars(ch - 'A') -= 1
    }
    chars.filter(_ != 0).size == 0
//    val g1 = s.filter(_.isLower).toSeq.map(_.toUpper).groupBy(identity).view.mapValues(_.length).toMap
//    val g2 = s.filter(_.isUpper).toSeq.groupBy(identity).view.mapValues(_.length).toMap
//    g1.equals(g2)
  }
  var ans = Int.MaxValue
  var start = 0
  var end = 0

  for(f <- 1 until str.length) {
    for(fast <- f until str.length) {
      tmp.append(str(fast))
      while (isBalanced(tmp.toString())) {
        println(s"Balanced => $tmp")
        if (ans > fast - slow) {
          start = slow
          end = fast
          println(s"$start and $end")
        }
        ans = Math.min(ans, fast - slow)
        tmp.deleteCharAt(0)
        println(s"After Removing => $tmp")
        slow += 1
      }
    }
    slow += f
    tmp = new StringBuilder()
  }
  if(ans == Int.MaxValue) "" else str.substring(start, end)
}

balancedString("azABaabza")
"CATt".substring(1, 3)