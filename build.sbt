import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ali.books",
      scalaVersion := "2.12.5",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "BookCoding",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaSwing
  )
