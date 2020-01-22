package com.scala.learn.basic.lectures.part5.oop.commands

import com.scala.learn.basic.lectures.part5.oop.filesystem.State

/* Created by imransarwar on 2020-01-13 */
class Cat(filename: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd

    val dirEntry = wd.findEntry(filename)
    if (dirEntry == null || !dirEntry.isFile)
      state.setMessage(filename + ": no such file")
    else
      state.setMessage(dirEntry.asFile.contents)
  }
}
