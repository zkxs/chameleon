import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._

object Dependencies {

  lazy val dependencies = Seq(
    "org.scala-js" %%%! "scalajs-dom" % "0.9.6" // https://github.com/vmunier/play-scalajs.g8/issues/20
  )

  lazy val testLibraries: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.0.3"
  ).map(_ % Test)

  lazy val extraResolvers = Seq(
  )

}
