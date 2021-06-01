package de.simple.game.engine

import org.slf4j.LoggerFactory

object Simple {

  private val Log = LoggerFactory.getLogger(Simple.getClass)

  def doIt(x: Int): Int = {
    Log.info(s"Input parameter = '$x'.")

    x + 3
  }
}
