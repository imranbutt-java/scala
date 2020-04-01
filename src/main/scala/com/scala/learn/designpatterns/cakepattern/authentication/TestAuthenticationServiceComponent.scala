package com.scala.learn.designpatterns.cakepattern.authentication

trait TestAuthenticationServiceComponent {

  val testAuthenticationService : AuthenticationService

  class TestAuthenticationService extends AuthenticationService {
    override def authenticate(userInfo: String): Boolean = true
  }

}

