package com.evolutiongaming.random

import cats.arrow.FunctionK
import munit.FunSuite

class RandomApplySpec extends FunSuite {

  private implicit val random: Random[Random.State.Type] =
    Random.State(123456789L).mapK(FunctionK.id)

  test("apply") {
    assertEquals(Random[Random.State.Type].int, Random[Random.State.Type].int)
  }
}
