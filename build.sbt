name := "mapslt-downloader"
version := "0.1"
scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.1"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.2"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"


mainClass in (Compile, run) := Some("parku30.MapsltToKml")