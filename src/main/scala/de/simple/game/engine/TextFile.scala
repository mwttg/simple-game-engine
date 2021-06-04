package de.simple.game.engine

import org.slf4j.LoggerFactory

import scala.io.Source
import scala.util.{ Try, Using }

object TextFile {

  private val Log = LoggerFactory.getLogger(this.getClass)

  /**
    * Reads a text file (from /resources) and returns a Try monad of the lines (as Vector of String).
    *
    * @param resourceFilename the filename inside the resources folder
    * @return lines of this text file as a Vector in a Try monad
    */
  def read(resourceFilename: String): Try[Vector[String]] = {
    Log.debug(s"Try to read content from file: '$resourceFilename'.")
    Using(Source.fromResource(resourceFilename))(file => file.getLines().toVector)
  }
}
