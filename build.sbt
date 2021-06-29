import Dependencies._

name := "random"

organization := "com.evolutiongaming"

homepage := Some(new URL("http://github.com/evolution-gaming/random"))

startYear := Some(2019)

organizationName := "Evolution Gaming"

organizationHomepage := Some(url("http://evolutiongaming.com"))

scalaVersion := crossScalaVersions.value.head

crossScalaVersions := Seq("2.13.5", "2.12.14"/*, "0.22.0-RC1"*/)

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
  Cats.core.withDottyCompat(scalaVersion.value),
  CatsEffect.effect.withDottyCompat(scalaVersion.value),
  `cats-helper`.withDottyCompat(scalaVersion.value),
  scalatest % Test
)

licenses := Seq(("MIT", url("https://opensource.org/licenses/MIT")))

releaseCrossBuild := true

// this is required to use cats syntax without warnings
scalacOptions += "-language:implicitConversions"
Compile / doc / scalacOptions -= "-Xfatal-warnings"