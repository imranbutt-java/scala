//case class Book(title: String, authors: List[String])
//case class Movie(title: String)
//
//val authors = List("A", "B", "C")
//val books = List(Book("B1", List("A", "B")),
//  Book("B2", List("C")),
//  Book("B3", List("D", "F")))
//
//def bookAdaptation(author: String): List[Movie] = author match {
//  case "B" => List(Movie("X"), Movie("Y"))
//  case _ => List()
//}
//
//def recommendedBooks(friend: String): List[Book] = {
//  val scala = List(Book("S1", List("A1", "A2")),
//    Book("S2", List("A3")))
//
//  val fiction = List(Book("F1", List("AF1", "AF2")),
//    Book("F2", List("AF3")))
//
//  if("A" == friend) scala
//  else if("B" == friend) fiction
//  else List()
//}
//
//val movieList = authors.map(a => bookAdaptation(a))
//movieList.flatten
//
//authors.flatMap(a => bookAdaptation(a))
//
////Coffee Break
//val recommendation = List("A", "B").flatMap(a => recommendedBooks(a))
//recommendation.flatMap(b => b.authors)
//
////Chained FlatMap and maps
//val movies = books.flatMap(b => b.authors)
//  .flatMap(a => bookAdaptation(a))
//
//// I don't have access to author, use nested flatmap
////val requiredFeeds = movies.map(m => s"You may like ${m.title}, because, you liked $author's ${b.title}")
//
//val feeds = books.flatMap(b => {
//  b.authors.flatMap(a => {
//    bookAdaptation(a).map(m => s"You may like ${m.title}, because, you liked $a's ${b.title}")
//  })
//})

val segmentLength = 2
val hardDiskSpace = List(2,3,1,6,5,4)
val g = hardDiskSpace.grouped(segmentLength).map(x => x.min).toList.max


