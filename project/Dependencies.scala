import sbt._

object Dependencies {

  val `cats-helper` = "com.evolutiongaming" %% "cats-helper" % "3.7.0"
  val scalatest     = "org.scalatest"       %% "scalatest"   % "3.2.16"

  object Cats {
    private val version = "2.9.0"
    val core   = "org.typelevel" %% "cats-core" % version
  }

  object CatsEffect {
    private val version = "3.5.1"
    val effect = "org.typelevel" %% "cats-effect" % version
  }
}
