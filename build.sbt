import Dependencies._

name := "random"
organization := "com.evolution"
homepage := Some(url("https://github.com/evolution-gaming/random"))
startYear := Some(2019)
organizationName := "Evolution"
organizationHomepage := Some(url("https://evolution.com"))

scalaVersion := crossScalaVersions.value.head
crossScalaVersions := Seq("2.13.18", "3.9.0")
versionPolicyIntention := Compatibility.BinaryCompatible

scalacOptions += "-Wunused:imports"

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
  scalatest % Test,
)

autoAPIMappings := true

licenses := Seq(("MIT", url("https://opensource.org/licenses/MIT")))

Test / publishArtifact := false

scmInfo := Some(
  ScmInfo(
    url("https://github.com/evolution-gaming/random"),
    "git@github.com:evolution-gaming/random.git",
  ),
)

developers := List(
  Developer(
    "t3hnar",
    "Yaroslav Klymko",
    "yklymko@evolution.com",
    url("https://github.com/t3hnar"),
  ),
)

publishTo := Some(Resolver.evolutionReleases)

addCommandAlias("fmt", "all scalafmtRepo")
addCommandAlias("check", "all versionPolicyCheck Compile/doc scalafmtCheckRepo")
addCommandAlias("build", "+all compile test")
