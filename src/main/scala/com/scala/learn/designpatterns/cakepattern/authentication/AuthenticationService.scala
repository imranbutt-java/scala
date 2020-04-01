package com.scala.learn.designpatterns.cakepattern.authentication

trait AuthenticationService {

  /**
    * Any new Third Party Authentication, must implement this trait
    */
  def authenticate(userInfo: String): Boolean
}
