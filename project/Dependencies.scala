import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.3"
  lazy val scalaz = "org.scalaz" %% "scalaz-core" % "7.2.15"
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4"
  lazy val cats = "org.typelevel" %% "cats-core" % "1.0.0-RC1"
}
