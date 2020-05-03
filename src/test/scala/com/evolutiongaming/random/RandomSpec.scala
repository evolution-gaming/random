package com.evolutiongaming.random

import cats.arrow.FunctionK
import munit.FunSuite

class RandomSpec extends FunSuite {

  private val random: Random[Random.State.Type] =
    Random.State(123456789L).mapK(FunctionK.id)

  test("int") {
    assert(random.int == random.int)
    val (random1, a0) = random.int
    assertEquals(a0, 1883)
    val (_, a1) = random1.int
    assertEquals(a1, 1820451251)
  }

  test("long") {
    assertEquals(random.long, random.long)
    val (random1, a0) = random.long
    assertEquals(a0, 8089243869619L)
    val (_, a1) = random1.long
    assertEquals(a1, 5245808146714613004L)
  }

  test("float") {
    assertEquals(random.float, random.float)
    val (random1, a0) = random.float
    assertEquals(a0, 4.172325E-7f)
    val (_, a1) = random1.float
    assertEquals(a1, 0.4238568f)
  }

  test("double") {
    assertEquals(random.double, random.double)
    val (random1, a0) = random.double
    assertEquals(a0, 4.3844963359962463E-7)
    val (_, a1) = random1.double
    assertEquals(a1, 0.2843758208196805)
  }

}
