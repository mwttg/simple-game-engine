package de.simple.game.engine

import org.scalatest.{ Matchers, WordSpec }

import java.io.FileNotFoundException
import scala.util.Success

class TextFileTest extends WordSpec with Matchers {

  "TextFile" should {
    "read a text file correctly" in {
      val actual = TextFile.read("a-valid-text-file.txt")
      actual shouldBe Success(Vector("1. line", "2. line", "3. line"))
    }

    "handle errors" in {
      val actual = TextFile.read("a-file-that-does-not-exists.txt")
      actual.isFailure shouldBe true
      actual.failed.get shouldBe a[FileNotFoundException]
    }
  }
}
