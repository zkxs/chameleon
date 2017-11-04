import Dependencies._

enablePlugins(ScalaJSPlugin)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "net.michaelripley",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "chameleon",
    resolvers ++= extraResolvers,
    libraryDependencies ++= testLibraries,
    libraryDependencies ++= dependencies,
    scalaJSUseMainModuleInitializer := true
  )
