package com.scala.learn.interview.design
import scala.collection.mutable
import scala.collection.mutable._
/* imransarwar created on 30/11/2020*/
object LFUCache extends App {
  class LFUCache(_capacity: Int) {
    val cache = LinkedHashMap[Int, (Int, Int)]().withDefaultValue((-1, 0))
    val cacpacity = _capacity

    def get(key: Int): Int = {
      if(cache.contains(key)) {
        val (v, count) = cache(key)
        cache.remove(key)
        cache.put(key, (v, count + 1))
      }
      cache(key)._1
    }

    def put(key: Int, value: Int) {
      if(cache.contains(key)) cache.put(key, (value, cache(key)._2 + 1))
      else if(cache.size == cacpacity) {
        cache.remove(cache.minBy(_._2._2)._1)
        cache.put(key, (value, 1))
      }
      else cache.put(key, (value, 1))
    }
  }

  val lfu = new LFUCache(2)
  lfu.put(2, 1)
  lfu.put(3, 2)
  lfu.get(3)
  lfu.get(2)
  lfu.put(4, 3)
  println(lfu.get(2))
  println(lfu.get(3))
}
