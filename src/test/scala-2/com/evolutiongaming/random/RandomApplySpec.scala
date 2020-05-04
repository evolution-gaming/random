package com.evolutiongaming.random

import cats.arrow.FunctionK
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class RandomApplySpec extends AnyFunSuite with Matchers {

  private implicit val random: Random[Random.State.Type] =
    Random.State(123456789L).mapK(FunctionK.id)

  test("apply") {
    Random[Random.State.Type].int shouldEqual Random[Random.State.Type].int
  }
}
