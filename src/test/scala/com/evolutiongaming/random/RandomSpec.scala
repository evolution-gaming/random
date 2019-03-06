package com.evolutiongaming.random

import cats.arrow.FunctionK
import org.scalatest.{FunSuite, Matchers}

class RandomSpec extends FunSuite with Matchers {

  private val random = Random.State(123456789l).mapK(FunctionK.id)

  test("int") {
    random.int shouldEqual random.int
    val (random1, a0) = random.int
    a0 shouldEqual 1883
    val (_, a1) = random1.int
    a1 shouldEqual 1820451251
  }

  test("long") {
    random.long shouldEqual random.long
    val (random1, a0) = random.long
    a0 shouldEqual 8089243869619l
    val (_, a1) = random1.long
    a1 shouldEqual 5245808146714613004l
  }

  test("float") {
    random.float shouldEqual random.float
    val (random1, a0) = random.float
    a0 shouldEqual 4.172325E-7f
    val (_, a1) = random1.float
    a1 shouldEqual 0.4238568f
  }

  test("double") {
    random.double shouldEqual random.double
    val (random1, a0) = random.double
    a0 shouldEqual 4.3844963359962463E-7
    val (_, a1) = random1.double
    a1 shouldEqual 0.2843758208196805
  }
}
