package com.evolutiongaming.random

import cats.Id
import cats.arrow.FunctionK
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class RandomSummonSpec extends AnyFunSuite with Matchers {

  given Random[Random.State.Type] =
    Random.State(123456789L).mapK(FunctionK.id)

  test("summon") {
    summon[Random[Random.State.Type]].int shouldEqual summon[Random[Random.State.Type]].int
  }

}
