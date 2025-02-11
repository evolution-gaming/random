package com.evolutiongaming.random

import cats.effect.unsafe.implicits.global
import cats.effect.{IO, Ref}
import cats.syntax.all._
import org.scalatest.Succeeded
import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers

class RandomStateOfTest extends AsyncFunSuite with Matchers {

  test("RandomStateOf") {
    val result = for {
      randomStateOf <- RandomStateOf.of[IO]
      random <- randomStateOf()
      ref <- Ref[IO].of(random)
      value = ref.modify { _.double }
      values <- List.fill(1000)(value).sequence
      _ <- IO {
        values.foreach { value =>
          value should be <= 1.0
          value should be >= 0.0
        }
      }
    } yield {}
    result
      .as(Succeeded)
      .unsafeToFuture()
  }
}
