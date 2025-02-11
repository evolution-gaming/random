package com.evolutiongaming.random

import cats.effect.Sync
import cats.syntax.all._

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
          def apply() = {
            seedOf().map { seed =>
              Random.State((seed ^ 0x5deece66dL) & ((1L << 48) - 1))
            }
          }
        }
      }
  }
}
