package com.evolutiongaming.random

import cats.syntax.all._
import cats.effect.Sync

trait RandomStateOf[F[_]] {
  def apply(): F[Random.State]
}

object RandomStateOf {

  private abstract sealed class Main

  def of[F[_]: Sync]: F[RandomStateOf[F]] = {
    SeedOf
      .fromClock[F]
      .map { seedOf =>
        new Main with RandomStateOf[F] {
          def apply(): F[Random.State] = {
            seedOf().map { seed => Random.State((seed ^ 0x5DEECE66DL) & ((1L << 48) - 1)) }
          }
        }
      }
  }
}
