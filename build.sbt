name := "Exercises"

version := "0.1"

scalaVersion := "2.13.4"

val fs2_version = "2.5.0"

libraryDependencies += "co.fs2" %% "fs2-core" % fs2_version // For cats 2 and cats-effect 2
libraryDependencies += "co.fs2" %% "fs2-io" % fs2_version // optional I/O library
libraryDependencies += "co.fs2" %% "fs2-reactive-streams" % fs2_version // optional reactive streams interop
libraryDependencies += "co.fs2" %% "fs2-experimental" % fs2_version // optional experimental library

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.2.0" withSources() withJavadoc()

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0")