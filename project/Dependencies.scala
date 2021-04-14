import sbt._

object Dependencies {

  val `cats-helper` = "com.evolutiongaming" %% "cats-helper" % "2.1.4"
  val scalatest     = "org.scalatest"       %% "scalatest"   % "3.2.7"

  object Cats {
    private val version = "2.5.0"
    val core   = "org.typelevel" %% "cats-core"   % version
    val effect = "org.typelevel" %% "cats-effect" % "2.4.1"
  }
}
