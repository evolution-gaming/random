import sbt.*

object Dependencies {

  val `cats-helper` = "com.evolutiongaming" %% "cats-helper" % "3.13.1"
  val scalatest = "org.scalatest" %% "scalatest" % "3.2.20"

  object Cats {
    private val version = "2.13.0"
    val core = "org.typelevel" %% "cats-core" % version
  }

  object CatsEffect {
    private val version = "3.7.0"
    val effect = "org.typelevel" %% "cats-effect" % version
  }
}
