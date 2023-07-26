import Dependencies._

name := "random"

organization := "com.evolutiongaming"

homepage := Some(new URL("http://github.com/evolution-gaming/random"))

startYear := Some(2019)

organizationName := "Evolution"

organizationHomepage := Some(url("http://evolution.com"))

scalaVersion := crossScalaVersions.value.head

crossScalaVersions := Seq("2.13.11", "2.12.18", "3.3.0")

publishTo := Some(Resolver.evolutionReleases)

Compile / unmanagedSourceDirectories += {
  if (scalaVersion.value startsWith "2")
    sourceDirectory.value / "main" / "scala-2"
  else
    sourceDirectory.value / "main" / "scala-3"
}

Test / unmanagedSourceDirectories += {
  if (scalaVersion.value startsWith "2")
    sourceDirectory.value / "test" / "scala-2"
  else
    sourceDirectory.value / "test" / "scala-3"
}

libraryDependencies ++= Seq(
  Cats.core,
  CatsEffect.effect,
  `cats-helper`,
  scalatest % Test
)

licenses := Seq(("MIT", url("https://opensource.org/licenses/MIT")))

releaseCrossBuild := true

// this is required to use cats syntax without warnings
scalacOptions += "-language:implicitConversions"
Compile / doc / scalacOptions -= "-Xfatal-warnings"
