import Dependencies._

name := "random"

organization := "com.evolutiongaming"

homepage := Some(new URL("http://github.com/evolution-gaming/random"))

startYear := Some(2019)

organizationName := "Evolution Gaming"

organizationHomepage := Some(url("http://evolutiongaming.com"))

bintrayOrganization := Some("evolutiongaming")

scalaVersion := crossScalaVersions.value.head

crossScalaVersions := Seq("2.13.3", "2.12.12", "3.0.0-RC2")

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

resolvers += Resolver.bintrayRepo("evolutiongaming", "maven")

libraryDependencies ++= Seq(
  Cats.core.cross(CrossVersion.for3Use2_13),
  Cats.effect.cross(CrossVersion.for3Use2_13),
  `cats-helper`.cross(CrossVersion.for3Use2_13),
  scalatest % Test
)

licenses := Seq(("MIT", url("https://opensource.org/licenses/MIT")))

releaseCrossBuild := true

// this is required to use cats syntax without warnings
scalacOptions += "-language:implicitConversions"
Compile / doc / scalacOptions -= "-Xfatal-warnings"
