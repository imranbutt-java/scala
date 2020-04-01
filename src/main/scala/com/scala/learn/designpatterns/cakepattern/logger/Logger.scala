package com.scala.learn.designpatterns.cakepattern.logger

trait Logger {
  def log(info: String): Unit
  def error(info: String): Unit
}
