import sbt._

object Dependencies {

  val `cats-helper` = "com.evolutiongaming" %% "cats-helper" % "3.0.1"
  val scalatest     = "org.scalatest"       %% "scalatest"   % "3.1.1"

  object Cats {
    private val version = "2.9.0"
    val core   = "org.typelevel" %% "cats-core" % version
  }

  object CatsEffect {
    private val version = "3.3.4"
    val effect = "org.typelevel" %% "cats-effect" % version
  }
}