package com.evolutiongaming.random

import cats.effect.kernel.Ref
import cats.effect.{Clock, Sync}
import cats.syntax.all._
import com.evolutiongaming.catshelper.ClockHelper._

trait SeedOf[F[_]] {
  def apply(): F[Long]
}

object SeedOf {
  private abstract sealed class FromClock

  def fromClock[F[_]: Sync]: F[SeedOf[F]] = {
    for {
      ref <- Ref[F].of(8682522807148012L)
    } yield {
      new FromClock with SeedOf[F] {
        def apply(): F[Long] = {
          for {
            nanos      <- Clock[F].nanos
            uniquifier <- ref.updateAndGet { _ * 1181783497276652981L }
          } yield {
            nanos ^ uniquifier
          }
        }
      }
    }
  }
}
