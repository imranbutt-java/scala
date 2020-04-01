package com.scala.learn.designpatterns.cakepattern.authentication

/* imransarwar created on 30/03/2020*/
trait OAuthAuthenticationServiceComponent {
  val oauthAuthenticationService: AuthenticationService = new OAuthAuthenticationService

  class OAuthAuthenticationService extends AuthenticationService {
    override def authenticate(userInfo: String): Boolean = userInfo.split('&')(1).equals("password=cake-pattern")
  }
}
