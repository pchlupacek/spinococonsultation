organization := "spinoco"

scalaVersion := "2.10.2"

name := "spinococonsultation"

version := "0.1.0-SNAPSHOT"

libraryDependencies += "org.scalaz.stream" %% "scalaz-stream" % "0.1-SNAPSHOT" withSources()

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.0" withSources()

libraryDependencies +=  "org.specs2" %% "specs2" % "2.1.1" % "test" withSources()

resolvers ++= Seq(Resolver.sonatypeRepo("releases"), Resolver.sonatypeRepo("snapshots"))

