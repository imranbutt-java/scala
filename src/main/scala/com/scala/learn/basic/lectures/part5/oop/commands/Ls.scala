package com.scala.learn.basic.lectures.part5.oop.commands

import com.scala.learn.basic.lectures.part5.oop.files.DirEntry
import com.scala.learn.basic.lectures.part5.oop.filesystem.State

/* Created by imransarwar on 2020-01-13 */
class Ls extends Command {

  override def apply(state: State): State = {
    val contents = state.wd.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }

  def createNiceOutput(contents: List[DirEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "]\n" + createNiceOutput(contents.tail)
    }
  }
}
