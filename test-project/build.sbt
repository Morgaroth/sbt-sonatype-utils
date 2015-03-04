import sbt.Keys._

name := "test-sbt-maven-utils-proj"

version := "0.1.0"

enablePlugins(SbtSonatypeUtils)

description := githubPom("fdsfds", "fdsf", "fdsgfd").toString
