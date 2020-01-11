package com.scala.learn

object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello World");

    println("<<<<< Returning immutable List, shows how cryptic scala code and use of expressions >>>>>")
    println("List of Iteration = "+List("A", "B", "C").map(_ == "B"))

    println("<<<<< Nested Expression, and only last expression would be used for the outer value >>>>>\n")
    val radius = 3
    val area = {
      //Note here I need to add semicolon
      val PI = 3.14;
      {
        val PI = 3.2
        println(s"New PI: $PI")
        PI * radius * radius
      }
      PI * radius * radius
    }
    println("Area = "+area)

    println("<<<<< if else statement example >>>>>\n")
    val a = "A"
    val b = "B"
    val c = {
      if(a == "A") {
        a+"C"
      } else {
        b+"C"
      }
    }
    println(s"If else expression: $c")

    println("<<<<< What loop should be avoided >>>>>>\n")
    val names = List("Imran", "Mash", "Sash")
    for(name <- names) {
      name match {
        case "Imran" => println(s"Hello, it is $name")
        case otherName => println(s"No, it is not Imran")
      }
    }

    println("<<<<< What loop should be implemented >>>>>>\n")
    val weeks = List("Monday", "Tuesday", "Wednesday")
    val today = for( day <- weeks) yield {
      day match {
        case "Monday" => s"Hello, it is $day"
        case otherName => "No, it is not Imran"
      }
    }

  }
}
