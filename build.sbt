name := "scala"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value)