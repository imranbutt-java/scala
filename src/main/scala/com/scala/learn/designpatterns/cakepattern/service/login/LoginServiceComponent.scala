package com.scala.learn.designpatterns.cakepattern.service.login

import com.scala.learn.designpatterns.cakepattern.authentication.{
  AzureAuthenticationServiceComponent,
  OAuthAuthenticationServiceComponent
}
import com.scala.learn.designpatterns.cakepattern.logger.ApplicationLoggerComponent

trait LoginServiceComponent {

  self: OAuthAuthenticationServiceComponent
    with AzureAuthenticationServiceComponent
    with ApplicationLoggerComponent =>

  val loginService: LoginService

  class LoginService {

    def login(userInfo: String): Boolean = {
      val userAuthenticated = azureAuthenticationService.authenticate(userInfo)
      // val userAuthenticated = oauthAuthenticationService.authenticate(userInfo)

      if (userAuthenticated) applicationLogger.log(userInfo)
      else applicationLogger.error(userInfo)
      userAuthenticated
    }
  }
}
