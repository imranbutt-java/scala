package com.scala.learn.basic.lectures.part3fp

import scala.util.Random

/**
 * Options are used where we expect some Null values and we don't want to crash the application
 * Options return
 * Some = If value exist
 * None = If nothing exist
 */
object Options extends App {
  val someOption: Option[Int] = Some(4)
  val noneOption: Option[Int] = None
  println(someOption)
  println(noneOption)

  //Work Unsafe API
  def unsafeMethod(): String = null
  //NOTE: It is totally wrong to use function on Some() that returns null
  //Some should never hold null
//  val result: Option[String] = Some(unsafeMethod())
  val result = Option(unsafeMethod())
  println(result)

  //What to do, when we want a default string instead of null
  //Chained Method
  def backupMethod(): String = "Backup Result."
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
  println(chainedResult)

  //Design Unsafe API
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("Backup Result.")
  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()

  println(betterChainedResult)

  //Functions on Options
  println(someOption.isEmpty)
  println(someOption.get) // UNSAFE, Don't use it

  //map, flatMap, filter
  //Just for reminder
  println(someOption.map(new Function1[Int, Int] {
    override def apply(x: Int): Int = x * 2
  }))
  println(someOption.map(x => x * 2))
  println(someOption.map(_ * 2))

  println(someOption.filter(x => x > 12))
  println(someOption.flatMap(x => Option(x * 3)))

  //EXERCISE
  //Some team provide you an api, Once connected run connect method
  //This config file may provide us host/port and may not too
  val config: Map[String, String] = Map(
    "host" -> "176.45.36.1",
    "port" -> "80"
  )
  class Connection {
    def connect = "Connected" //Actually, connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] = {
      if(random.nextBoolean()) Some(new Connection)
      else None
    }
  }

  // SOLUTION IN DETAIL
  val host = config.get("host")
  val port = config.get("port")
  /*
  What is an imperative logic:
  if(host != null)
    if(port != null)
      return Connection.apply(h, p)

   return null
   */
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))
  /*
  Same what the logic, we are expecting here
  if(connection != null)
    return c.connect
  return null
   */
  val connectionStatus = connection.map(c => c.connect)
  /*
   if(connectionStatus == null)
     println(None)
   else
     print(Some(connectionStatus.get))
   */
  println(s"Connection: $connectionStatus")
  /*
  if(status == null)
    println(status)
   */
  connectionStatus.foreach(println)

  // SOLUTION IN SHORT
  config.get("host")
    .map(host => config.get("port")
    .flatMap(port => Connection(host, port))
    .map(connection => connection.connect)
    .foreach(println))

  // SOLUTION WITH FOR-COMPREHENSION
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    con <- Connection(host, port)
  } yield con.connect

  println(s"Connection Status: $forConnectionStatus")

}
