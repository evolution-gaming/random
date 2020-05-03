package com.evolutiongaming.random

import cats.Id
import cats.arrow.FunctionK
import munit.FunSuite

class RandomSummonSpec extends FunSuite {

  given Random[Random.State.Type] =
    Random.State(123456789L).mapK(FunctionK.id)

  test("summon") {
    assertEquals(summon[Random[Random.State.Type]].int, summon[Random[Random.State.Type]].int)
  }

}
