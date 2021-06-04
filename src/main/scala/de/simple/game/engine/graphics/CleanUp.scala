package de.simple.game.engine.graphics

import org.lwjgl.glfw.GLFW
import org.slf4j.LoggerFactory

/**
  * This is the CleanUp service for the whole OpenGL stuff. Here Memory gets freed up, ids get released and so on.
  * This service should be called when the application quits. It's not an ideal architecture decision, because the
  * fields are not immutable and the object acts like a kind of God object for all Ids. But it will work for now ;)
  */
object CleanUp {

  private val Log = LoggerFactory.getLogger(this.getClass)

  private var windowId = 0L

  /** Set the OpenGL window ID */
  def setWindowId(id: Long): Unit = windowId = id

  /** Start the clean up now */
  def now(): Unit = {
    Log.info("Starting clean up process.")
    cleanUpOpenGlWindow()
  }

  private def cleanUpOpenGlWindow(): Unit = {
    Log.debug("  Cleaning up OpenGL window.")
    GLFW.glfwDestroyWindow(windowId)
    GLFW.glfwTerminate()
  }
}
