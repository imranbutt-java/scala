package com.scala.learn.interview.design

import scala.collection.mutable._
/* imransarwar created on 11/01/2021*/
object WordDictionary extends App {

  /** Initialize your data structure here. */
  case class Node(child: Map[Char, Node] = Map[Char, Node](), var isWord: Boolean = false)
  val root = Node()

  def addWord(word: String) {
    var node = root
    for(ch <- word) {
      if(!node.child.contains(ch))
        node.child.put(ch, Node())
      node = node.child(ch)
    }
    node.isWord = true
  }

  def searchUtil(word: String, r: Node): Boolean = {
    var node = r
    for((ch, i) <- word.zipWithIndex) {
      if(!node.child.contains(ch)) {
      // if the current character is '.'
      // check all possible nodes at this level
        if(ch == '.') {
          for(c <- node.child.keySet) {
            val child = node.child(c)
            if(searchUtil(word.substring(i+1), child))
              return true
          }
          // if no nodes lead to answer
          // or the current character != '.'
          return false
        }
      } else {
        // if the character is found
        // go down to the next level in trie
        node = node.child(ch)
      }
    }
    node.isWord
  }

  def search(word: String): Boolean = {
    searchUtil(word, root)
  }

  addWord("mad")
  search("pad")

}
