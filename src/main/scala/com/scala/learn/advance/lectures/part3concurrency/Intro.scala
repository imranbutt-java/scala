package com.scala.learn.advance.lectures.part3concurrency

import java.util.concurrent.Executors

/* imransarwar created on 15/02/2020*/
object Intro extends App {

  // JVM Thread
  // Threads are most likely instance of a class
  /*
  Thread class takes a Runnable interface
  interface Runnable {
    public void run();
  }
   */
  val aRunnable = new Runnable {
    override def run(): Unit = println("Run in Parallel")
  }
  val aThread = new Thread(aRunnable)

  // start() method actually makes thread to run on OS Thread
  // aThread thread instance's start method sends signal to JVM to actually start JVM Thread
  // where actually println(...) runs
  aThread.start()
  // This calling run method doesn't start any thread to run in parallel
  aRunnable.run()
  //blocks until aThread finishes running
  aThread.join()

  val helloThread = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val byeThread = new Thread(() => (1 to 5).foreach(_ => println("bye")))

  helloThread.start()
  byeThread.start()

  // executors, manage thread pool and let us free from managing threads
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("I am thread in a pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("Slept for 1 second at : "+System.currentTimeMillis())
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("2nd: Slept for 1 sec at : "+System.currentTimeMillis())
    Thread.sleep(1000)
    println("Done after 2 sec")
  })

  // Pool shutdown, so next call of execute would throw exception, bcz pool is no more accepting execute
  pool.shutdown()
//  pool.execute(() => println("After shutdown"))

  // Thread sleeping for 1 or 2 second, would throw exception as pool is already closed
//  pool.shutdownNow()

  def runInParallel = {
    var x = 0

    val oneThread = new Thread(() => x = 1)
    val twoThread = new Thread(() => x = 2)
    oneThread.start()
    twoThread.start()

    // It may print the value of x, even oneThread and twoThread never started, but x may be anything
    println(x)
  }

  // Observe Race Condition
  //for(_ <- 1 to 10) runInParallel

  //See what it may impact in a Bank Application
  class BankAccount(var amount: Int) {
    //override def toString: String = s"Amount: $amount"
  }

  def buy(account: BankAccount, product: String, price: Int) = {
    //Here threads would take amount == 50,000 for most of their calculation
    account.amount -= price
    printTransaction(account, product, price)
  }

  def printTransaction(account: BankAccount, product: String, price: Int): Unit = {
    println
    println(s"|-----$product Bought----|")
    println(s"|----- Price: $price-----|")
    println(s"|Bank Amount: ${account.amount}----|")
    println(s"|---------------------|")
  }

  /*
  val account = new BankAccount(6000)
  for(_ <- 1 to 10) {
    val buyer1 = new Thread(() => buy(account, "Shoes", 200))
    val buyer2 = new Thread(() => buy(account, "Phone", 300))

    buyer1.start()
    buyer2.start()
    Thread.sleep(10)
  }
   */

  //So the above code faces race condition in buy method and to fix this issue, we have multiple options

  //Option 1: Use Synchronized
  def buySafe(account: BankAccount, product: String, price: Int) = {
    account.synchronized {
      account.amount -= price
      printTransaction(account, product, price)
    }
  }

  // (200 + 300) * 10 = 5,000
  // Remaining : 1000
  val account = new BankAccount(6000)
  for(_ <- 1 to 10) {
    val buyer1 = new Thread(() => buySafe(account, "Shoes", 200))
    val buyer2 = new Thread(() => buySafe(account, "Phone", 300))

    buyer1.start()
    buyer2.start()
    Thread.sleep(10)
  }

  // Option 2: Use Volatile


  /**
   * Exercises
   *
   * 1) Construct 50 "inception" threads
   *     Thread1 -> thread2 -> thread3 -> ...
   *     println("hello from thread #3")
   *   in REVERSE ORDER
   *
   */
  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread $i")
  })

  inceptionThreads(10).start()

  /*
    2
   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  /*
    1) what is the biggest value possible for x? 100
    2) what is the SMALLEST value possible for x? 1

    thread1: x = 0
    thread2: x = 0
      ...
    thread100: x = 0

    for all threads: x = 1 and write it back to x
   */
  threads.foreach(_.join())
  println(x)

  /*
    3 sleep fallacy
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(1001)
  // wait for the awesome thread to join, means main thread wait here till awesome Thread has finished
  awesomeThread.join()
  println(message)
  /*
    what's the value of message? almost always "Scala is awesome"
    is it guaranteed? NO!
    why? why not?

    (main thread)
      message = "Scala sucks"
      awesomeThread.start()
      sleep() - relieves execution
    (awesome thread)
      sleep() - relieves execution
    (OS gives the CPU to some important thread - takes CPU for more than 2 seconds)
    (OS gives the CPU back  to the MAIN thread)
      println("Scala sucks")
    (OS gives the CPU to awesomethread)
      message = "Scala is awesome"

   */

  // how do we fix this?
  // syncrhonizing does NOT work, bcz it is sequential problem not concurrent problem
  // So we have to use join, and wait till the desired thread finish the job.
  // Note: Sleeping threads to sequence the threads is a bad practice
}
