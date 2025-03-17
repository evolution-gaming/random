# Random
[![Build Status](https://github.com/evolution-gaming/random/workflows/CI/badge.svg)](https://github.com/evolution-gaming/random/actions?query=workflow%3ACI)
[![Coverage Status](https://coveralls.io/repos/evolution-gaming/random/badge.svg)](https://coveralls.io/r/evolution-gaming/random)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/20c2455aa4e14e8a85b362f3e508383d)](https://app.codacy.com/gh/evolution-gaming/random/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Artifactory](https://img.shields.io/github/v/release/evolution-gaming/random)](https://evolution.jfrog.io/ui/packages/gav:%2F%2Fcom.evolution:random_2.13)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellowgreen.svg)](https://opensource.org/licenses/MIT)

Pure deterministic pseudo-random number generator, which returns a new state on each call.
The returned state could be used to generate a next random value, without
relying for it to be saved in a mutable variable inside of the RNG library.

It could be useful for writing the code, when the direct usage of an effect
system such as `cats.effect.IO` is either not possible or not desired. Also,
the versions `0.1.1` and below are compatible with Cats Effect 2.

If the application already uses Cats Effect 3 and pure random generator is not
required then https://typelevel.org/cats-effect/docs/std/random might be a
preferred choice for sake of using a more standard solution.

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

## Usage

The library could be used directly with pure Scala without any additional effect systems:
```scala
def currentTimeSeedExample(): Unit = {
  // note, that current time could be a bad choice for a seed
  val state0 = Random.State(System.currentTimeMillis())
  val (state1, n0) = state0.int
  val (_, n1) = state1.int
  println(s"Two random numbers: $n0, $n1")
}
```

It also includes facilities to create a random number generator with a safe seed based on the current time from Cats Effect `Clock` instance:
```scala
def clockSeedExample: IO[Unit] =
  for {
    state0 <- Random.State.fromClock[IO]()
    (state1, n0) = state0.int
    (_, n1) = state1.int
    _ <- IO.println(s"Two random numbers: $n0, $n1")
  } yield ()
```

The typical Cats Effect user might prefer storing these instances in `IOLocal`
for efficiency and code readability (i.e. to not pass a state manually around):

``` scala
def ioLocalExample: IO[Unit] =
  for {
    random <- IOLocalRandom.fromClock
    n0 <- random.int
    n1 <- random.int
    _ <- IO.println(s"Two random numbers: $n0, $n1")
  } yield ()
  
object IOLocalRandom {

  def fromClock: IO[Random[IO]] =
    for {
      random <- Random.State.fromClock[IO]()
      random <- IOLocal(random)
    } yield new Random[IO] {
      def int: IO[Int] = random.modify(_.int)
      def long: IO[Long] = random.modify(_.long)
      def float: IO[Float] = random.modify(_.float)
      def double: IO[Double] = random.modify(_.double)
    }

}
```

It is also possible to use the same approach in a Tagless Final environment
using `ThreadLocalOf` from Cats Helper library:

```scala
import com.evolutiongaming.catshelper.ThreadLocalOf

def threadLocalExample[F[_]: Monad: Clock: Console: ThreadLocalOf]: F[Unit] =
  for {
    random <- ThreadLocalRandom.fromClock[F]
    n0 <- random.int
    n1 <- random.int
    _ <- Console[F].println(s"Two random numbers: $n0, $n1")
  } yield ()

object ThreadLocalRandom {

  def fromClock[F[_]: Monad: Clock: ThreadLocalOf]: F[Random[F]] =
    for {
      random <- Random.State.fromClock[F]()
      random <- ThreadLocalOf[F].apply(random)
    } yield new Random[F] {
      def int: F[Int] = random.modify(_.int)
      def long: F[Long] = random.modify(_.long)
      def float: F[Float] = random.modify(_.float)
      def double: F[Double] = random.modify(_.double)
    }

}
```