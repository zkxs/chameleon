import Dependencies._

enablePlugins(ScalaJSPlugin)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      scalaVersion := "2.12.6"
    )),
    organization := "net.michaelripley",
    name := "chameleon",
    version := "0.1.0-SNAPSHOT",
    resolvers ++= extraResolvers,
    libraryDependencies ++= testLibraries,
    libraryDependencies ++= dependencies,
    scalaJSUseMainModuleInitializer := true,
    relativeSourceMaps := true
  )
