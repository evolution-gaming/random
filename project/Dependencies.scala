import sbt._

object Dependencies {

  val `cats-helper` = "com.evolutiongaming" %% "cats-helper" % "2.1.4"
  val scalatest     = "org.scalatest"       %% "scalatest"   % "3.1.1"

  object Cats {
    private val version = "2.3.0"
    val core   = "org.typelevel" %% "cats-core"   % version
    val effect = "org.typelevel" %% "cats-effect" % version
  }
}