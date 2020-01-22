package com.scala.learn.basic.lectures.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure extends App {
  // Create success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

  println(aSuccess)
  println(aFailure)

  def unsafeMethod: String = throw new RuntimeException("NO STRING FOR YOU")

  // This Try is a companion object that is using apply method
  val potentialFailure = Try(unsafeMethod)
  println(potentialFailure)

  //Syntactic Sugar
  val anotherPotentialFailure = Try {
    // Code may throw exception
  }

  //Utilities
  println(potentialFailure.isSuccess)

  //orElse
  def backUpMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod) orElse Try(backUpMethod())
  println(fallbackTry)

  /*
    If you design the API
    Try is used as a same analogy of Option
    Code may throw Exception use Try
    Code may throw null use Option
   */
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("Better Backup")
  val betterFallback = betterUnsafeMethod orElse betterBackupMethod

  println(betterFallback)

  //map, flatMap and filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 3)))
  //aSuccess has element value 3 and that failure would turn into an exception
  //Converts this to a Failure if the predicate is not satisfied
  println(aSuccess.filter(_ > 10))

  //As map, filter and flatMap works on Try, so for-comprehension also works

  // EXERCISE
  val host = "localhost"
  val port = "8080"
  def renderHTML(page: String) =  println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if(random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection Interrupted, Try Again.")
    }

    // We created that
    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    def getConnection(host: String, port: String): Connection =
      if (new Random(System.nanoTime()).nextBoolean()) new Connection
      else throw new RuntimeException("Service is down, Try again")

    def getSafe(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  //If you get the connection then render the page
  val possibleConnection = HttpService.getSafe(host, port)
  val possibleHTML = possibleConnection.flatMap(con => con.getSafe("http://google.com"))
  println("First solution...")
  possibleHTML.foreach(renderHTML)


  //Short Solution
  println("Short...")
  HttpService.getSafe(host, port)
    .flatMap(con => con.getSafe("http://google.com"))
    .foreach(renderHTML)

  //For-comprehension
  println("For comprehension...")
  for {
    con <- HttpService.getSafe(host, port)
    html <- con.getSafe("http://google.com")
  } yield renderHTML(html)

  /*
  Equivalent logic
  try {
    connection = HttpService.getConnection(host, port)
    try {
      page = connection.get("/home")
      renderHTML(page)
    } catch(Exception e) {
        ...
    }
  } catch(Exception e) {
      ...
  }
   */
}
