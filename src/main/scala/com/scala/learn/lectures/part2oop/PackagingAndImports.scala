package com.scala.learn.lectures.part2oop

import com.scala.learn.playground.{Cinderalla => Princess, PrinceCharming}
import java.util.Date
import java.sql.{Date => SQLDate}

/* Created by imransarwar on 2020-01-12 */
object PackagingAndImports extends App {
  //Package member are accessible
  val writer: Writer = new Writer("Naseem", "Hijazi", 1945)

  //Imports
  val cinderalla = new Princess
  //Package Object
  //One package has one package object
  //That has functions and constants, that are accessible by their name

  sayHello
  //import com.scala.learn.playground.{Cinderalla, PrinceCharming}
  //If there is long list being imported it could be written as
  //import com.scala.learn.playground._
  val prince = new PrinceCharming

  //Class aliases would be used
  //import com.scala.learn.playground.{Cinderalla => Princess, PrinceCharming} and it is good, when more than
  //one name of 2 classes in the same class
  //Here java would by default consider the first import
  val date = new Date
  val sqlDate = new SQLDate(2020, 1, 12)

  //default imports
  //java.lang, String, Object, Exception etc
  //Scala - Int, Nothing, Function
  //scala.Predef - println, ???
}
