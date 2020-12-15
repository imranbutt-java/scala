package com.scala.learn.interview.design

import scala.collection.mutable._
import scala.util.control.Breaks._

case class Bucket() {
  case class Pair[T](_key: T, _value: T) {
    var key = _key
    var value = _value
  }
  val bucket = ListBuffer[Pair[Int]]()

  def get(key: Int): Int = {
    for(p <- bucket) {
      if(p.key.equals(key)) return p.value
    }
    -1
  }

  def update(key: Int, value: Int): Unit = {
    var found = false
    for(p <- bucket) {
      if(p.key.equals(key)) {
        p.value = value
        found = true
      }
    }
    if(!found) bucket.addOne(Pair(key, value))
  }

  def remove(key: Int) {
    breakable {
      for (p <- bucket) {
        if (p.key.equals(key)) {
          bucket.remove(bucket.indexOf(p))
          break
        }
      }
    }
  }
}

object MyHashMap {
  var keySpace: Int = 2069
  var hashTable = ListBuffer.fill(keySpace)(Bucket())
}
class MyHashMap() {

  /** Initialize your data structure here. */
  import MyHashMap._

  /** value will always be non-negative. */
  def put(key: Int, value: Int) {
    val hashKey = key % keySpace
    hashTable(hashKey).update(key, value)
  }

  /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
  def get(key: Int): Int = {
    val hashKey = key % keySpace
    hashTable(hashKey).get(key)
  }

  /** Removes the mapping of the specified value key if this map contains a mapping for the key */
  def remove(key: Int) {
    val hashKey = key % keySpace
    hashTable(hashKey).remove(key)
  }

}

object Main extends App {
  var obj = new MyHashMap()
  obj.put(1,100)
  var param_2 = obj.get(1)
  obj.remove(1)
}