name := "expand"

organization in ThisBuild := "es.kathars"

scalaVersion in ThisBuild := "2.10.2"

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers += Resolver.sonatypeRepo("snapshots")

addCompilerPlugin("org.scala-lang.plugins" % "macro-paradise" % "2.0.0-SNAPSHOT" cross CrossVersion.full)

libraryDependencies in ThisBuild <++= scalaVersion { (sv: String) => Seq(
  "org.scala-lang" % "scala-compiler" % sv,
  "org.scala-lang" % "scala-reflect" % sv,
  "org.scala-lang" % "scala-actors" % sv,
  "org.scalatest" %% "scalatest" % "1.9.1" % "test"
)}
