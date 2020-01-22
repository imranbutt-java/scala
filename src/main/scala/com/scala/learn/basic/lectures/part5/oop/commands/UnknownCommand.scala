package com.scala.learn.basic.lectures.part5.oop.commands

import com.scala.learn.basic.lectures.part5.oop.filesystem.State

/* Created by imransarwar on 2020-01-13 */
class UnknownCommand extends Command {

  override def apply(state: State): State =
    state.setMessage("Command not found!")
}
