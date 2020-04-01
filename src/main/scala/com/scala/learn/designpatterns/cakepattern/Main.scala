package com.scala.learn.designpatterns.cakepattern

object Main extends App {
  import config.ComponentRegistry.loginService

  loginService.login("username=scala-user&password=cake-pattern")
}
