package com.scala.learn.advance.lectures.part4implicits

/* imransarwar created on 28/02/2020*/
trait MyTypeClassTemplate[T] {
  def action(value: T): String
}

object MyTypeClassTemplate {
  def apply[T](implicit instance: MyTypeClassTemplate[T]) = instance
}