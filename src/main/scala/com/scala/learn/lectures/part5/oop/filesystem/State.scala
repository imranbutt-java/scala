package com.scala.learn.lectures.part5.oop.filesystem

import com.scala.learn.lectures.part5.oop.files.Directory

/* Created by imransarwar on 2020-01-13 */
class State(val root: Directory, val wd: Directory, val output: String) {

  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String): State =
    State(root, wd, message)

}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State =
    new State(root, wd, output)
}
