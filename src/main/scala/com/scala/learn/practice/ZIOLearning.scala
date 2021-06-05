package com.scala.learn.practice

import java.io.IOException

sealed trait Cause[+E]

object Cause {

  final case class Die(t: Throwable) extends Cause[Nothing]

  final case class Fail[+E](e: E) extends Cause[E]

}

trait ZIO[-R, +E, +A] {
  def sandbox: ZIO[R, Cause[E], A]

  def unsandbox[E1](implicit ev: E <:< Cause[E1]): ZIO[R, E1, A]

  def orDie(implicit ev: E <:< Throwable): ZIO[R, Nothing, A] = ???

  def orElse[R1 <: R, E2, A1 >: A]
  (
    that: ZIO[R1, E2, A1]
  ): ZIO[R1, E2, A1] = ???

  final case class ZIO[-R, +E, +A](run: R => Either[Cause[E], A]) {
    self =>
    def foldCauseM[R1 <: R, E1, B]
    (
      failure: Cause[E] => ZIO[R1, E1, B],
      success: A => ZIO[R1, E1, B]
    ): ZIO[R1, E1, B] =
      ZIO(r => self.run(r).fold(failure, success).run(r))
  }

}

object Check {
  def readFile(file: String): ZIO[Any, IOException, String] = ???

  lazy val result: ZIO[Any, IOException, String]#ZIO[Any, Nothing, String] = readFile("data.txt").orDie
}

final case class ApiError(r: String)
final case class DbError(r: String)

object CombineErrors {
  lazy val callApi: ZIO[Any, ApiError, String] = ???
  lazy val callDb: ZIO[Any, DbError, Int] = ???

}



