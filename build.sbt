ThisBuild / organization := "com.github.mwttg"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = project
  .in(file("."))
  .settings(
    name := "simple-game-engine",
    libraryDependencies ++=
      lwjglDependencies ++
      testingDependencies :+
      dependencies.wavefrontReader :+
      dependencies.slf4j
  )

// ------------ [ DEPENDENCIES ] ----------------

lazy val dependencies = new {

  val osClassifier = "macos" // TODO: Change to "linux" or "windows" if necessary

  val jomlVersion            = "1.9.19"
  val lwjglVersion           = "3.2.3"
  val scalaTestVersion       = "3.0.8"
  val slf4jVersion           = "1.7.30"
  val wavefrontReaderVersion = "1.0.0"

  val joml              = "org.joml"                  % "joml"              % jomlVersion
  val lwjgl             = "org.lwjgl"                 % "lwjgl"             % lwjglVersion
  val lwjglNative       = "org.lwjgl"                 % "lwjgl"             % lwjglVersion classifier s"natives-$osClassifier"
  val lwjglGlfw         = "org.lwjgl"                 % "lwjgl-glfw"        % lwjglVersion
  val lwjglGlfwNative   = "org.lwjgl"                 % "lwjgl-glfw"        % lwjglVersion classifier s"natives-$osClassifier"
  val lwjglOpenGl       = "org.lwjgl"                 % "lwjgl-opengl"      % lwjglVersion
  val lwjglOpenGlNative = "org.lwjgl"                 % "lwjgl-opengl"      % lwjglVersion classifier s"natives-$osClassifier"
  val lwjglStb          = "org.lwjgl"                 % "lwjgl-stb"         % lwjglVersion
  val lwjglStbNative    = "org.lwjgl"                 % "lwjgl-stb"         % lwjglVersion classifier s"natives-$osClassifier"
  val scalactic         = "org.scalactic"             %% "scalactic"        % scalaTestVersion
  val scalatest         = "org.scalatest"             %% "scalatest"        % scalaTestVersion % "test"
  val slf4j             = "org.slf4j"                 % "slf4j-api"         % slf4jVersion
  val wavefrontReader   = "com.github.thesortedchaos" %% "wavefront-reader" % wavefrontReaderVersion
}

lazy val lwjglDependencies = Seq(
  dependencies.lwjgl,
  dependencies.lwjglNative,
  dependencies.lwjglGlfw,
  dependencies.lwjglGlfwNative,
  dependencies.lwjglOpenGl,
  dependencies.lwjglOpenGlNative,
  dependencies.lwjglStb,
  dependencies.lwjglStbNative,
  dependencies.joml
)

lazy val testingDependencies = Seq(
  dependencies.scalactic,
  dependencies.scalatest
)

// ------------ [ COMMAND ALIAS ] ----------------

addCommandAlias(
  "build-complete",
  "; scalafmtCheck ; scalafmtSbtCheck ; compile ; test"
)
