# Random
[![Build Status](https://github.com/evolution-gaming/random/workflows/CI/badge.svg)](https://github.com/evolution-gaming/random/actions?query=workflow%3ACI)
[![Coverage Status](https://coveralls.io/repos/evolution-gaming/random/badge.svg)](https://coveralls.io/r/evolution-gaming/random)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/20c2455aa4e14e8a85b362f3e508383d)](https://app.codacy.com/gh/evolution-gaming/random/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Artifactory](https://evolution.jfrog.io/ui/packages/gav:%2F%2Fcom.evolution:random_2.13)](https://img.shields.io/badge/Artifact-1.0.5-blue?style=flat-square&logo=jfrog)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellowgreen.svg)](https://opensource.org/licenses/MIT)

```scala
trait Random[F[_]] {

  def int: F[Int]

  def long: F[Long]

  def float: F[Float]

  def double: F[Double]
}
```

## Setup

```scala
addSbtPlugin("com.evolution" % "sbt-artifactory-plugin" % "0.0.2")

libraryDependencies += "com.evolution" %% "random" % "1.0.5"
```
