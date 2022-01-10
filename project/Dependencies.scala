import sbt._

object Dependencies {

  val `cats-helper` = "com.evolutiongaming" %% "cats-helper" % "2.7.1-SNAPSHOT" // FIXME CE3 version required
  val scalatest     = "org.scalatest"       %% "scalatest"   % "3.1.1"

  object Cats {
    private val version = "2.6.1"
    val core   = "org.typelevel" %% "cats-core" % version
  }

  object CatsEffect {
    private val version = "3.3.3"
    val effect = "org.typelevel" %% "cats-effect" % version
  }
}