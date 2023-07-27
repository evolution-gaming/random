# Random
[![Build Status](https://github.com/evolution-gaming/random/workflows/CI/badge.svg)](https://github.com/evolution-gaming/random/actions?query=workflow%3ACI)
[![Coverage Status](https://coveralls.io/repos/evolution-gaming/random/badge.svg)](https://coveralls.io/r/evolution-gaming/random)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9d6a16a40ec34f7480894583b303b1a4)](https://www.codacy.com/app/evolution-gaming/random?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=evolution-gaming/random&amp;utm_campaign=Badge_Grade)
[![Version](https://img.shields.io/badge/version-click-blue)](https://evolution.jfrog.io/artifactory/api/search/latestVersion?g=com.evolutiongaming&a=random_2.13&repos=public)
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

libraryDependencies += "com.evolution" %% "random" % "1.0.4"
```
