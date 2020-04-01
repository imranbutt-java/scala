package com.scala.learn.designpatterns.cakepattern.config

import com.scala.learn.designpatterns.cakepattern.authentication.{AuthenticationService, AzureAuthenticationServiceComponent, OAuthAuthenticationServiceComponent}
import com.scala.learn.designpatterns.cakepattern.logger.{ApplicationLoggerComponent, Logger}
import com.scala.learn.designpatterns.cakepattern.service.login.LoginServiceComponent

object ComponentRegistry extends LoginServiceComponent
  with OAuthAuthenticationServiceComponent
  with AzureAuthenticationServiceComponent
  with ApplicationLoggerComponent {

  override val applicationLogger: Logger = new ApplicationLogger
  override val azureAuthenticationService: AuthenticationService = new AzureAuthenticationService
  override val oauthAuthenticationService: AuthenticationService = new OAuthAuthenticationService
  override val loginService: LoginService = new LoginService

}
