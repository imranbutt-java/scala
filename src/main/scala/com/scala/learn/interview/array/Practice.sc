import scala.collection.mutable._

// FoldLeft
val words = List("Scala", "Java")
words.foldLeft((0, 0))((total, word) => {
  println(s"$total, $word")
  (total._1 + word.length, total._2 + total._1)
})

// Sorted Map
val sortedMap = LinkedHashMap[Char, Int]()
sortedMap.addOne('a' -> 1)
sortedMap.addOne('b' -> 2)
sortedMap.addOne('c' -> 3)

sortedMap.remove('a')
sortedMap.remove(sortedMap.last._1)
sortedMap

// Returns Int
3/2

// Solving Expression
def calculate(s: String): Int = {
  val operandStack = Stack[Int]()
  val operatorStack = Stack[Char]()

  def getPrecedence(c: Char): Int = c match {
    case x if x == '*' || x == '/' => 1
    case _ => 0
  }
  def operation(x: Int, op: Char, y: Int): Int = op match {
    case '*' => x * y
    case '/' => x / y
    case '+' => x + y
    case '-' => x - y
  }

  val n = s.length
  var i = 0
  while(i < n) {
    if(s(i).isDigit) {
      var num = 0
      while(i < n && s(i).isDigit) {
        num = num * 10 + s(i) - '0'
        i += 1
      }
      i -= 1
      operandStack.push(num)
    } else if(' ' != s(i)) {
      val precedence = getPrecedence(s(i))
      while(operatorStack.nonEmpty && precedence <= getPrecedence(operatorStack.head)) {
        val op = operatorStack.removeHead(true)
        val op1 = operandStack.removeHead(true)
        val op2 = operandStack.removeHead(true)
        operandStack.push(operation(op1, op, op2))
      }
      operatorStack.push(s(i))
    }
    i += 1
  }
  while(operatorStack.nonEmpty) {
    val op = operatorStack.removeHead(true)
    val op1 = operandStack.removeHead(true)
    val op2 = operandStack.removeHead(true)
    operandStack.push(operation(op1, op, op2))
  }
  operandStack.top
}

calculate(" 3/2 ")