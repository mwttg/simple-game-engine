package de.simple.game.engine

import org.scalatest.{Matchers, WordSpec}

class SimpleTest extends WordSpec with Matchers {

  "A Simple" should {
    "do it" in {
      val actual = Simple.doIt(2)
      actual shouldBe 5
    }
  }
}
