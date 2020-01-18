package com.scala.learn.lectures.part3fp

import scala.annotation.tailrec

/* Created by imransarwar on 2020-01-18 */
object TuplesAndMaps extends App {
  // Tuples = Finite Ordered List, of atmost 22 bcz they are used in conjunction with Function Types
  // Tuple2(Int, String) == (Int, String) syntactic sugar
  val aTuples: Tuple2[Int, String] = (2, "Hello")
  val bTuples = (3, "Hello", 5.3)

  println(aTuples._1)
  println(aTuples.copy(_2 = "By Java :D"))
  println(aTuples.swap)

  //Maps
  println("@Maps...")
  val aMap: Map[String, Int] = Map()
  // "Umar" -> 777 is syntactic sugar for Tuples (a, b)
  val phoneBook = Map(("Imran", 555), "Umar" -> 777).withDefaultValue(-1)
  println(phoneBook)

  //Map ops
  println(phoneBook.contains("Umar"))
  println(s"${phoneBook("Imran")} |  ${phoneBook("Hussain")}")
  /*
  If withDefaultValue is not used
  try {
    phoneBook("Hussain")
  } catch {
    case e: Exception => {
      println(e.getMessage)
    }
  }
  */
  //Now going to add new phone
  // "Hussain" -> 999 + phoneBook won't work
  // Also phoneBook + "Hussain" -> 999 won't make newPhonebook a map
  val newPair = "Hussain" -> 999
  val newPhonebook = phoneBook + newPair
  println(newPhonebook)

  //Functions on Map
  // map, filter, flatMap
  // Below is not working.
  //println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))
  println(newPhonebook.map(pair => pair._1.toLowerCase -> pair._2))
  println(newPhonebook.filter(x => x._1.startsWith("H")))
  println(newPhonebook.mapValues(v => v * 10))

  //Map to List Conversion
  println(newPhonebook.toList)
  //Tuple is passed in List
  println(List( ("A", 12) ).toMap)

  val names = List("Imran", "Bushra", "Ashtalfa", "Muhammad", "Hussain", "Umar", "Ali")
  println(names.groupBy(names => names.charAt(0)))

  //EXERCISE
  /*
    1. What if may has "Im" -> 2 and "im" -> 2?
    Ans: Map key is not case sensitive, but if lowercase function applied, they would overlap
    2. Overly simplified social network based on maps
       Person = String
       - add a person to the network
       - remove
       - friend (mutual)
       - unfriend

       - number of friends of a person
       - person with most friends
       - how many people have NO friends
       - if there is a social connection between 2 people (direct or indirect)
   */

  // Ans for 1
  val m = Map("Im" -> 1, "im" -> 2)
  println(m)

  /**
    * Adding Person in Network
    * @param network
    * @param person
    * @return
    */
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    network + (person -> Set())
  }

  def friend(network: Map[String, Set[String]], p: String, f: String): Map[String, Set[String]] = {
    val pFriends = network(p)
    val fFriends = network(f)
    network + (p -> (pFriends + f)) + (f -> (fFriends + p))
  }

  def unFriend(network: Map[String, Set[String]], p: String, f: String): Map[String, Set[String]] = {
    val pFriends = network(p)
    val fFriends = network(f)
    network + (p -> (pFriends - f)) + (f -> (fFriends - p))
  }

  def remove(network: Map[String, Set[String]], p: String): Map[String, Set[String]] = {
    //instead of using for loop we are going to use tail rec
    @tailrec
    def removeAux(fList: Set[String], networkAux: Map[String, Set[String]]): Map[String, Set[String]] =
      if(fList.isEmpty) networkAux
      else removeAux(fList.tail, unFriend(networkAux, p, fList.head))

    val unfriended = removeAux(network(p), network)
    unfriended - p
  }

  def nFriends(network: Map[String, Set[String]], p: String): Int = {
    if(!network.contains(p)) 0
    else network(p).size
  }

  def mostFriend(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
  }

  def alone(network: Map[String, Set[String]]): Set[String] = {
    network.filter(pair => pair._2.isEmpty).keySet
  }

  def socialConnection(network: Map[String, Set[String]], p: String, f: String): Boolean = {
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if(discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if(person == target) true
        else if(consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }

    bfs(f, Set(), network(p) + p)
  }

  println("@ Exercies...")
  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Hussain"), "Imran")
  println(network)
  println(friend(network, "Hussain", "Imran"))
  println(unFriend(friend(network, "Hussain", "Imran"), "Hussain", "Imran"))
  println(remove(friend(network, "Hussain", "Imran"), "Imran"))

  //Jim, Mary, Bob
  val people = add(add(add(add(empty, "Jim"), "Mary"), "Bob"), "Imran")
  val jimBob = friend(people, "Jim", "Bob")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)
  println(nFriends(testNet, "Bob"))
  println(mostFriend(testNet))
  println(alone(testNet))
  println(socialConnection(testNet, "Jim", "Mary"))
  println(socialConnection(testNet, "Jim", "Imran"))
}
