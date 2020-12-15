package com.scala.learn.interview.design

import scala.collection.mutable

/* imransarwar created on 30/11/2020*/
object LRUCache extends App {
  class LRUCache(_capacity: Int) {
    val capacity = _capacity
    val cache = mutable.LinkedHashMap[Int, Int]().withDefaultValue(0)

    def get(key: Int): Int = {
      if(cache.contains(key)) {
        val value = cache.remove(key).get
        cache.put(key, value)
      }
      cache(key)
    }

    def put(key: Int, value: Int) {
      if(cache.size == capacity) cache.remove(cache.head._1)
      else cache.remove(key)
      cache.put(key, value)
    }
  }
}
