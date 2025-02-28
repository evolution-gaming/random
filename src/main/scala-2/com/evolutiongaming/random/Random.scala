package com.evolutiongaming.random

import cats.data.StateT
import cats.effect.Clock
import cats.implicits._
import cats.{FlatMap, Id, ~>}
import com.evolutiongaming.catshelper.ClockHelper._

trait Random[F[_]] {

  def int: F[Int]

  def long: F[Long]

  def float: F[Float]

  def double: F[Double]
}

object Random {

  /** The type used as a seed for the random number generator.
    *
    * In this library it also used as an internal state of the random number
    * generator.
    */
  type Seed = Long

  def apply[F[_]](implicit F: Random[F]): Random[F] = F

  implicit class RandomOps[F[_]](val self: Random[F]) extends AnyVal {

    def mapK[G[_]](f: F ~> G): Random[G] = new Random[G] {

      def int = f(self.int)

      def long = f(self.long)

      def float = f(self.float)

      def double = f(self.double)
    }
  }

  /** The random number generator for a single specific type `A`.
    *
    * It takes some `state1` as an input and returns a new `state2` and a random
    * value of type `A`.
    *
    * Technically, it is just a function from `(Seed)` to `(Seed, A)`.
    *
    * `StateT` is used instead of a plain function, got have the ability to
    * chain several calls in for comprehensions, instead of doing something like
    * following:
    * ```
    * val (state1, a) = f(seed)
    * val (state2, b) = g(state1)
    * val (state3, c) = h(state2)
    * ```
    *
    * The practice shown that this introduces a lot of confusion, so in future
    * library versions `StateT` will stop to be exposed in public API.
    */
  type SeedT[A] = StateT[Id, Seed, A]

  object SeedT {

    /** Set of random number generators for common numeric types.
      *
      * The instance provides `SeedT`
      */
    val Random: Random[SeedT] = {

      val doubleUnit = 1.0 / (1L << 53)

      val floatUnit = (1 << 24).toFloat

      def next(bits: Int): SeedT[Int] =
        SeedT { seed =>
          val r = (seed >>> (48 - bits)).toInt
          val s1 = (seed * 0x5deece66dL + 0xbL) & ((1L << 48) - 1)
          (s1, r)
        }

      new Random[SeedT] {

        def int: SeedT[Int] = next(32)

        def long: SeedT[Long] =
          for {
            a0 <- next(32)
            a1 = a0.toLong << 32
            a2 <- next(32)
          } yield {
            a1 + a2
          }

        def float: SeedT[Float] =
          for {
            a <- next(24)
          } yield {
            a / floatUnit
          }

        def double: SeedT[Double] =
          for {
            a0 <- next(26)
            a1 = a0.toLong << 27
            a2 <- next(27)
          } yield {
            (a1 + a2) * doubleUnit
          }

      }
    }

    def apply[A](f: Seed => (Seed, A)): SeedT[A] =
      StateT[Id, Seed, A] { seed => f(seed) }
  }

  /** Snapshot of a state of a stateful random number generator.
    *
    * @param seed
    *   The internal state of the random number generator that will be used to
    *   generate the next random number.
    * @param random
    *   The stateless part of the random number generator, i.e. the set of
    *   functions from `state1` to `(state2, A)`, where `A` is the type of
    *   outputs of a random number generator such as `Int`, `Long`, `Float`, or
    *   `Double`.
    */
  final case class State(seed: Seed, random: Random[SeedT] = SeedT.Random)
      extends Random[State.Type] {

    private def apply[A](stateT: SeedT[A]) = {
      val (seed1, a) = stateT.run(seed)
      (State(seed1, random), a)
    }

    def int: (State, Int) = apply(random.int)

    def long: (State, Long) = apply(random.long)

    def float: (State, Float) = apply(random.float)

    def double: (State, Double) = apply(random.double)
  }

  object State {

    type Type[A] = (State, A)

    /** Create an instance of [[Random.State]] based on [[cats.effect.Clock]].
      *
      * The state is initialized with a constant seed known to be good and mixed
      * together with a current time to add an additional randomness.
      */
    def fromClock[F[_]: Clock: FlatMap](
        random: Random[SeedT] = SeedT.Random
    ): F[State] =
      for {
        nanos <- Clock[F].nanos
      } yield {
        val seed =
          (nanos ^ 3447679086515839964L ^ 0x5deece66dL) & ((1L << 48) - 1)
        State(seed, random)
      }

  }
}
