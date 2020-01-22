package com.scala.learn.lectures.part5.oop.commands

import com.scala.learn.lectures.part5.oop.files.{File, DirEntry}
import com.scala.learn.lectures.part5.oop.filesystem.State

/* Created by imransarwar on 2020-01-13 */
class Touch(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)
}
