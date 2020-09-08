package com.scala.learn.interview.leetcode

import scala.collection.mutable

/* imransarwar created on 08/09/2020*/
object MostVisitedPattern extends App {
  def mostVisitedPattern(username: Array[String], timestamp: Array[Int], website: Array[String]): List[String] = {
    val n = username.length
    //step 1: sort by time stamp
    case class UserWeb(user: String, time: Int, web: String)
    val sessionsTmp = mutable.ListBuffer[UserWeb]()
    for((u, i) <- username.zipWithIndex) {
      sessionsTmp.addOne(UserWeb(u, timestamp(i), website(i)))
    }
    val sessions = sessionsTmp.toList.sortBy(_.time)
    // step 2: build Map<user, web sequence>
    val visited = mutable.Map[String, mutable.ListBuffer[String]]()
    for(session <- sessions) {
      if(!visited.contains(session.user)) visited.put(session.user, mutable.ListBuffer[String]())
      visited(session.user).addOne(session.web)
    }
    // step 3: build Map<3-seq, count>
    val seqMap = mutable.Map[String, Int]().withDefaultValue(0)
    var maxCount = 0
    var maxSeq = ""

    def getAll3SubSequences(list: List[String]): Set[String] = {
      val n = list.size
      val arr = list.toArray
      val set = mutable.Set[String]()
      for(i <- 0 to n)
        for(j <- i+1 until n)
          for(k <- j+1 until n) {
            set.add(s"${arr(i)},${arr(j)},${arr(k)}")
          }
      list.sliding(3, 3).map(x => x.reduce(_+","+_)).toSet
//      list.grouped(3).map(x => x.reduce((a,b) => {
//        if(a.isEmpty) b
//        else if(b.isEmpty) a
//        else a +","+ b
//      })).toSet
//      set.toSet
    }

    for(list <- visited.values) {
      if(list.size >= 3) {
        val subSequences = getAll3SubSequences(list.toList)
        for(subSeq <- subSequences) {
          seqMap.put(subSeq, seqMap(subSeq) + 1)
          if(seqMap(subSeq) > maxCount) {
            maxCount = seqMap(subSeq)
            maxSeq = subSeq
          } else if(seqMap(subSeq) == maxCount && maxSeq.compareTo(subSeq) > 0) {
            maxSeq = subSeq
          }
        }
      }
    }
    // step 4: build result list
    val res = mutable.ListBuffer[String]()
    res ++= maxSeq.split(",")
    res.toList
  }

//  val names = Array("joe","joe","joe","james","james","james","james","mary","mary","mary")
//  val time = Array(1,2,3,4,5,6,7,8,9,10)
//  val web = Array("home","about","career","home","cart","maps","home","home","about","career")
  val names = Array("dowg","dowg","dowg")
  val time = Array(2,3,1)
  val web = Array("y","loedo","y")
  mostVisitedPattern(names, time, web).foreach(x => print(s"$x,"))
}
