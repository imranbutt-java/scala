package com.scala.learn.advance.lectures.part4implicits

/* imransarwar created on 27/02/2020*/
object TypeClasses extends App {
  /*
  Ordering trade used to define implicit in OrganizingImplicits.scala are Type Class instances
  Type Class: is a trait that ticks a type and describes what operations can be applied to that type.

  Say we are developing app about html component rendering
   */
  trait HTMLWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHtml: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }

  User("John", 32, "john@rockthejvm.com").toHtml
  /*
    Disadvantages:
    There are few disadvantages, that we need to observe
    1 - It would only work, for the types WE write
        For other types, we have to make conversion
    2 - Multiple implementation need to be provided for any actions;
        ONE implementation out of quite a number
        Say, if some user not logged in, so for his toHtml, we have to show other message.
   */

  // option 2 - pattern matching, to handle these disadvantages
  object HTMLSerializerPM {
    def serializeToHtml(value: Any) = value match {
      case User(n, a, e) =>
      case _ =>
    }
  }

  /*
    Disadvantages on using pattern matching
    1 - lost type safety, as we see above (value: Any)
    2 - For any new component, we need to  modify the code every time
    3 - still ONE implementation, like logged out user, we can't show age and name etc
   */

  // Best Approach
  // HTMLSerializer is a Type Class and all those object that extends it and implement the funciton serialize
  // are Type Class Instances, and they are mostly made as Singleton
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  // Interesting Info:
  // Say we have a class String, so from this class we expect some common functions, like length of string.
  // and Type Checker in compiler can use this information to check the code compile time, to find the error
  // in the source code, this process is called Static Type Checking.
  // The same concept is used by Type Class and now if we say something is type T, so from Type Class we may
  // check compile time does that something satisfies all functions and properties of T, mentioned in Type Class.
  val john = User("John", 32, "john@rockthejvm.com")
  println(UserSerializer.serialize(john))

  // 1 - we can define serializers for other  types
  import java.util.Date
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString()}</div>"
  }

  // 2 - we can define MULTIPLE serializers
  object PartialUserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name} </div>"
  }

  // part 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    // Gives benefit, so that when we get the access to entire type interface
    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style: color=blue>$value</div>"
  }

  implicit object StringSerializer extends HTMLSerializer[String] {
    override def serialize(value: String): String = s"<div style: color=black>$value</div>"
  }

  // println(HTMLSerializer.serialize(42)(IntSerializer))
  // We may mark IntSerializer as implicit so we can remove (IntSerializer)
  // Benefit with this approach, now we may define StringSerializer and use syntactic sugar
  println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize("String 42"))
  // UserSerializer is set to implicit and now John may be used by HTMLSerializer.serialize method
  println(HTMLSerializer.serialize(john))

  // access to the entire type class  interface, bcz of HTMLSerializer.apply method
  println(HTMLSerializer[User].serialize(john))
  /*
    Exercise: implement the Type Class pattern for the equalit Type Class.
    Answered: EqualityPlayground
  */

  // part 3
  // How this class would benefit, so we may provide different Serializer for class type T
  // john.toHTML(UserSerializer) here explicitly calling serializer
  // john.toHTML(SomeNewSerializer)
  // john.toHTML
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHTML)  // println(new HTMLEnrichment[User](john).toHTML(UserSerializer))
  // COOL!
  /*
    - extend to new types
    - choose implementation
    - super expressive!
   */

  println(2.toHTML) // println(new HTMLEnrichment[Int](2).toHTML(IntSerializer)
  println(john.toHTML(PartialUserSerializer)) // explicitly calling other Serializer

  /*
  Type class here gives many things
    - type class itself --- HTMLSerializer[T] { .. }
    - type class instances (some of which are implicit) --- UserSerializer, IntSerializer
    - conversion with implicit classes --- HTMLEnrichment
   */

  // context bounds
  def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body> ${content.toHTML(serializer)}</body></html>"

  // Here HTMLSerializer is a context bound, that tells the compiler to inject
  // (implicit serializer: HTMLSerializer[T])
  // after
  // (content: T)
  // that makes the above method to write like below with syntactical sugar
  // But disadvantage, we can't inject serializer by name
  def htmlSugar[T : HTMLSerializer](content: T): String = {
    // Initially we use like this
    // s"<html><body> ${content.toHTML}</body></html>"
    // Applying implicitly would give an edge of using explicit Serializer
    val serializer = implicitly[HTMLSerializer[T]]
    // use serializer
    s"<html><body> ${content.toHTML(serializer)}</body></html>"
  }

  // implicitly
  // Let suppose, we have data privacy and we have to show the data on the screen, we have some permission stuff
  // Say someone made permissions in some where code
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the  code, we want to surface out what is the implicit values for permissions
  val standardPerms = implicitly[Permissions]
}