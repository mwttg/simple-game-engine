package de.simple.game.engine.mainloop

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11

object MainLoop {

  /**
    * This is the MainLoop (GameLoop) of the Simple Game Engine.
    * Here User inputs are collected and the window is updated.
    *
    * @param windowId The window ID of the created OpenGL window
    */
  def loop(windowId: Long): Unit = {
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)
      GLFW.glfwSwapBuffers(windowId)
      GLFW.glfwPollEvents()
    }
  }
}
