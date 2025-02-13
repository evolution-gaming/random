package com.evolutiongaming.random

import cats.effect.Clock
import cats.syntax.all.*
import cats.{FlatMap, Id, ~>}
import com.evolutiongaming.catshelper.ClockHelper.*

trait Random[F[_]] {
  def int: F[Int]
  def long: F[Long]
  def float: F[Float]
  def double: F[Double]
}

object Random {

  type Seed = Long

  def apply[F[_]](using F: Random[F]): Random[F] = F

  extension [F[_]](self: Random[F]) {
    def mapK[G[_]](f: F ~> G): Random[G] = new Random[G] {
      def int = f(self.int)
      def long = f(self.long)
      def float = f(self.float)
      def double = f(self.double)
    }
  }

  type SeedT[A] = cats.data.StateT[Id, Seed, A]

  object SeedT {

    val Random: Random[SeedT] = {

      val doubleUnit = 1.0 / (1L << 53)

      val floatUnit = (1 << 24).toFloat

      def next(bits: Int): SeedT[Int] = {
        SeedT { seed =>
          val r = (seed >>> (48 - bits)).toInt
          val s1 = (seed * 0x5deece66dL + 0xbL) & ((1L << 48) - 1)
          (s1, r)
        }
      }

      new Random[SeedT] {

        def int = next(32)

        def long = {
          for
            a0 <- next(32)
            a1 = a0.toLong << 32
            a2 <- next(32)
          yield a1 + a2
        }

        def float = {
          for a <- next(24)
          yield a / floatUnit
        }

        def double = {
          for
            a0 <- next(26)
            a1 = a0.toLong << 27
            a2 <- next(27)
          yield (a1 + a2) * doubleUnit
        }
      }
    }

    def apply[A](f: Seed => (Seed, A)): SeedT[A] =
      cats.data.StateT[Id, Seed, A] { seed => f(seed) }
  }

  final case class State(seed: Seed, random: Random[SeedT] = SeedT.Random)
      extends Random[State.Type] {

    private def apply[A](stateT: SeedT[A]) = {
      val (seed1, a) = stateT.run(seed)
      (State(seed1, random), a)
    }

    def int = apply(random.int)
    def long = apply(random.long)
    def float = apply(random.float)
    def double = apply(random.double)
  }

  object State { self =>

    type Type[A] = (State, A)

    def fromClock[F[_]: Clock: FlatMap](
        random: Random[SeedT] = SeedT.Random
    ): F[State] = {
      for nanos <- Clock[F].nanos
      yield
        val seed =
          (nanos ^ 3447679086515839964L ^ 0x5deece66dL) & ((1L << 48) - 1)
        apply(seed, random)
    }
  }
}
