package com.scala.learn.advance.lectures.part3concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success, Try}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/* imransarwar created on 20/02/2020*/
object FuturesPromises extends App {
  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates the  meaning of  life on ANOTHER thread
  } // (global) which is passed by the compiler


  println(aFuture.value) // Option[Try[Int]]

  println("Waiting on the future")
  // Future onComplete[U](f: Try[T] => U) takes function Try[Int] means, future may throw exception.
  // aFuture.onComplete(t => t match { ... })
  // That onComplete is a prototype of partial function, so we can replace with partial function of 2 cases
  // Also, onComplete[U] used for side effects because its return type Unit
  aFuture.onComplete {
    case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with $e")
  } // SOME thread would call this onComplete

  Thread.sleep(3000)

  // mini social network

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) =
      println(s"${this.name} poking ${anotherProfile.name}")
  }

  object SocialNetwork {
    // "database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy"
    )
    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    val random = new Random()

    // API
    def fetchProfile(id: String): Future[Profile] = Future {
      // fetching from the DB
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }
  }

  // client: mark to poke bill
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
  //  mark.onComplete {
  //    case Success(markProfile) => {
  //      val bill = SocialNetwork.fetchBestFriend(markProfile)
  //      bill.onComplete {
  //        case Success(billProfile) => markProfile.poke(billProfile)
  //        case Failure(e) => e.printStackTrace()
  //      }
  //    }
  //    case Failure(ex) => ex.printStackTrace()
  //  }


  // functional composition of futures
  // map, flatMap, filter
  // map transforms Future of a type Profile to another Future of type String
  // Future[Profile] => map => Future[String] as done below
  val nameOnTheWall = mark.map(profile => profile.name)
  val marksBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
  val zucksBestFriendRestricted = marksBestFriend.filter(profile => profile.name.startsWith("Z"))

  // As it has all 3 HOFs, we may go for
  // for-comprehensions
  // That implementation is clean than the one used with nested onComplete calls
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)

  Thread.sleep(1000)

  // fallbacks
  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("fb.id.0-dummy", "Forever alone")
  }

  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  val fallbackResult =  SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

  // online banking app
  // Example, when blocking is expected for waiting
  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "Rock the JVM banking"

    def fetchUser(name: String): Future[User] = Future {
      // simulate fetching from the DB
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      // simulate some processes
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "SUCCESS")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      // fetch the user from the DB
      // create a transaction
      // WAIT for the transaction to finish
      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      Await.result(transactionStatusFuture, 2.seconds) // implicit conversions -> pimp my library
    }
  }

  println(BankingApp.purchase("Daniel", "iPhone 12", "rock the jvm store", 3000))

  // promises

  val promise = Promise[Int]() // "controller" over a future
  val future = promise.future

  // thread 1 - "consumer"
  future.onComplete {
    case Success(r) => println("[consumer] I've received " + r)
  }

  // thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)
    // "fulfilling" the promise, it is promise pattern where consumer knows what to do
    // and producer promise what is required to mark for success
    promise.success(42)
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1000)

  /*
    1) fulfill a future IMMEDIATELY with a value
    2) inSequence(fa, fb)
    3) first(fa, fb) => new future with the first value of the two futures
    4) last(fa, fb) => new future with the last value
    5) retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T]
   */

  // 1 - fulfill immediately
  def fulfillImmediately[T](value: T): Future[T] = Future(value)
  // 2 - insequence
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] =
    first.flatMap(_ => second)

  // 3 - first out of two futures
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]
    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)

    promise.future
  }

  // 4 - last out of the two futures
  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    // 1 promise which both futures will try to complete
    // 2 promise which the LAST future will complete
    val bothPromise = Promise[A]
    val lastPromise = Promise[A]
    val checkAndComplete = (result: Try[A]) =>
      if(!bothPromise.tryComplete(result))
        lastPromise.complete(result)

    // Initially try catch block implemented. Reason: Because, when fa would mark promise.success(...) then
    // after that fb call for promise, it would throw exception
    /*
    1)
    fa.onComplete {
      case Success(r) => try {
          promise.success(r)
        } catch {
          case _ =>
        }
      case Failure(r) => try {
        } catch {
          case _ =>
        }
     }

     2)
     After that we decide to extract the common code
     def tryComplete(promise: Promise[A], result: Try[A]) = {
        case Success(r) => try {
          promise.success(r)
        } catch {
          case _ =>
        }
        case Failure(r) => try {
        } catch {
          case _ =>
        }
     }

     fa.onComplete(result => tryComplete(promise, result))
     ||
     fa.onComplete(tryComplete(promise, _))
     ||
     fa.onComplete(promise.tryComplete(_))
     ||
     Due to lifting it could be transformed to function value
     fa.onComplete(promise.tryComplete)

     Now say fa onComplete calls promise.success then later fb onComplete promise won't be called.
     */
    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)

    lastPromise.future
  }

  val fast = Future {
    Thread.sleep(100)
    42
  }

  val slow = Future {
    Thread.sleep(200)
    45
  }
  first(fast, slow).foreach(f => println("FIRST: " + f))
  last(fast, slow).foreach(l => println("LAST: "  +  l))

  Thread.sleep(1000)

  // retry until
  def retryUntil[A](action: () => Future[A], condition: A => Boolean): Future[A] =
    action()
      .filter(condition)
      .recoverWith { // When filter condition failed, recoverWith method would be called
        case _ => retryUntil(action, condition)
      }

  val random = new Random()
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) =>  x < 10).foreach(result => println("settled at " + result))
  Thread.sleep(10000)

  // NOTE:
  /*
  - Futures are immutable, read-only objects
  - Promises are writable-once container over a future
   */
}

