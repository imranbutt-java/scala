package com.scala.learn.basic.lectures.part5.oop.commands

import com.scala.learn.basic.lectures.part5.oop.files.{DirEntry, Directory}
import com.scala.learn.basic.lectures.part5.oop.filesystem.State

/* Created by imransarwar on 2020-01-13 */
abstract class CreateEntry(name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exists!")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separators!")
    } else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name!")
    } else {
      doCreateEntry(state, name)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def doCreateEntry(state: State, name: String): State = {
    /*
    It is a recursive function and creates all new instance even parents.
    This function returns a new instance (immutability) so pictorially it looks like
    /someDir
      /a
         /...
      /b

     /$ mkdir d (should result a new instance of someDir)
     /someDir
      /a
        /...
      /b
      /d

      Explanation:
      Say we are at root $/ and we want to create directory /e at /a/b
      /a/b
        /...
        (new Entry)/e
      updateStructure(root, ["a", "b"], /e)
        => path.isEmpty ?
        => oldEntry = /a
        root.replaceEntry("a", updateStructure(/a, ["b"], /e)
          => path.isEmpty?
          => oldEntry = /b
          /a.replaceEntry("b", updateStructure(/b, [], /e)
            => path.iEmpty? => /b.addEntry(/e)

     */
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd

    // 1. all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath

    // 2. create new directory entry in the wd
    val newEntry: DirEntry = createSpecificEntry(state)

    // 3. update the whole directory structure starting from the root
    // (the directory structure is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    // 4. find new working directory INSTANCE given wd's full path, in the NEW directory structure
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  def createSpecificEntry(state: State): DirEntry
}
