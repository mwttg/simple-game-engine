package de.simple.game.engine.graphics

import org.lwjgl.glfw.{ GLFW, GLFWErrorCallback }
import org.lwjgl.opengl.{ GL, GL11 }
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL
import org.slf4j.LoggerFactory

final case class Configuration(majorVersion: Int, minorVersion: Int, vsync: Boolean, wireframe: Boolean, backfaceCulling: Boolean)

final case class Window(title: String, width: Int, height: Int, configuration: Configuration)

object Window {

  private val Log = LoggerFactory.getLogger(this.getClass)

  /**
    * This function creates an OpenGL window. That window is also add to the [[CleanUp]] service.
    *
    * @param window A case class which stores the configuration of the OpenGL window
    * @return the OpenGL id of the window
    */
  def create(window: Window): Long = {
    initializeGlfw()
    configureWindow(window)
    val id = createWindow(window)
    CleanUp.setWindowId(id)
    setKeyCallback(id)
    applyGraphicConfiguration(id, window)
    centerWindow(id)

    id
  }

  private def initializeGlfw(): Unit = {
    Log.debug("Initializing GLFW.")
    GLFWErrorCallback.createPrint(System.err).set()
    if (!GLFW.glfwInit) {
      throw new RuntimeException("GLFW wasn't initialized correctly.")
    }
  }

  private def createWindow(window: Window) = {
    Log.info(s"Creating OpenGL window: '${window.title}' - ${window.width}x${window.height}.")
    val id = GLFW.glfwCreateWindow(window.width, window.height, window.title, NULL, NULL)
    if (id == NULL) {
      throw new RuntimeException("Unable to create OpenGL window.")
    }

    id
  }

  private def configureWindow(window: Window): Unit = {
    Log.debug(
      s"Configuring OpenGL window. Using OpenGL version ${window.configuration.majorVersion}.${window.configuration.minorVersion}."
    )
    GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_TRUE)
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE)
    GLFW.glfwWindowHint(
      GLFW.GLFW_CONTEXT_VERSION_MAJOR,
      window.configuration.majorVersion
    )
    GLFW.glfwWindowHint(
      GLFW.GLFW_CONTEXT_VERSION_MINOR,
      window.configuration.minorVersion
    )
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE)
    GLFW.glfwWindowHint(
      GLFW.GLFW_OPENGL_PROFILE,
      GLFW.GLFW_OPENGL_CORE_PROFILE
    )
  }

  private def setKeyCallback(id: Long): Unit = {
    Log.debug("Setting key callback function.")
    GLFW.glfwSetKeyCallback(
      id,
      (id, key, _, action, _) =>
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
          GLFW.glfwSetWindowShouldClose(id, true)
        }
    )
  }

  private def applyGraphicConfiguration(id: Long, window: Window): Unit = {
    Log.debug("Applying graphic configuration to OpenGL context.")

    GLFW.glfwMakeContextCurrent(id)
    GL.createCapabilities()
    GL11.glClearColor(0.3f, 0.3f, 0.3f, 1.0f)
    GLFW.glfwSwapInterval(if (window.configuration.vsync) 1 else 0)
    GLFW.glfwShowWindow(id)

    GL11.glEnable(GL11.GL_DEPTH_TEST)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    if (window.configuration.backfaceCulling) {
      GL11.glEnable(GL11.GL_CULL_FACE)
      GL11.glCullFace(GL11.GL_BACK)
    }
    if (window.configuration.wireframe) {
      GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE)
    }
  }

  private def centerWindow(id: Long): Unit = {
    Log.debug("Positioning the OpenGL window to the center.")
    val stack   = MemoryStack.stackPush()
    val pWidth  = stack.mallocInt(1)
    val pHeight = stack.mallocInt(1)
    GLFW.glfwGetWindowSize(id, pWidth, pHeight)
    val videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
    GLFW.glfwSetWindowPos(
      id,
      (videoMode.width() - pWidth.get(0)) / 2,
      (videoMode.height() - pHeight.get(0)) / 2
    )
  }
}
