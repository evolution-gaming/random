# Random [![Build Status](https://travis-ci.org/evolution-gaming/random.svg)](https://travis-ci.org/evolution-gaming/random) [![Coverage Status](https://coveralls.io/repos/evolution-gaming/random/badge.svg)](https://coveralls.io/r/evolution-gaming/random) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/9d6a16a40ec34f7480894583b303b1a4)](https://www.codacy.com/app/evolution-gaming/random?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=evolution-gaming/random&amp;utm_campaign=Badge_Grade) [ ![version](https://api.bintray.com/packages/evolutiongaming/maven/random/images/download.svg) ](https://bintray.com/evolutiongaming/maven/random/_latestVersion) [![License: MIT](https://img.shields.io/badge/License-MIT-yellowgreen.svg)](https://opensource.org/licenses/MIT)

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
resolvers += Resolver.bintrayRepo("evolutiongaming", "maven")

libraryDependencies += "com.evolutiongaming" %% "random" % "0.0.1"
```