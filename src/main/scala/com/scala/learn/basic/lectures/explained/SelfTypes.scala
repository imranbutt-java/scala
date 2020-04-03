package com.scala.learn.basic.lectures.explained

/* imransarwar created on 02/04/2020*/
object SelfTypes extends App {
  trait Donor {
    // Say all donor money
    val donatedMoney: Int = 12
  }

  trait CharityOrg {
    self: Donor =>
    val availableMoney: Int = donatedMoney
  }

  trait Needy {
    self: CharityOrg =>
    val needMoney: Int = availableMoney // + donatedMoney
  }
}
