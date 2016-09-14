name := "algorithms"

version := "1.0"

scalaVersion := "2.11.8"

// unit testing
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
libraryDependencies += "junit" % "junit" % "4.10"

// logging
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.21"

// required for junit output
testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

// Update main class as needed
mainClass in (Compile,run) := Some("Subset")