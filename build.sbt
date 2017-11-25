name := """odenktools-play"""
organization := "com.odenktools"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.2"

libraryDependencies += guice

libraryDependencies ++= Seq(
  jdbc,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "io.swagger" %% "swagger-play2" % "1.6.0" exclude("org.reflections", "reflections"),
  "org.webjars" % "swagger-ui" % "2.1.8-M1",
  "org.reflections" % "reflections" % "0.9.8" notTransitive (), 
  "commons-io" % "commons-io" % "2.4",
  "org.apache.poi" % "poi" % "3.8","org.apache.poi" % "poi-ooxml" % "3.9",
  "org.apache.httpcomponents" % "httpclient" % "4.3.3",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.4.1",
  "org.apache.logging.log4j" % "log4j-api" % "2.4.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.4.1"
)
