import SbtReleaseHelpers._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.ReleaseStep
import xerial.sbt.Sonatype._

sbtPlugin := true

name := "sbt-sonatype-utils"

organization := "io.github.morgaroth"

scalaVersion := "2.10.4"

releaseSettings

sonatypeSettings

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // : ReleaseStep
  inquireVersions, // : ReleaseStep
  clearTarget,
  runTest, // : ReleaseStep
  setReleaseVersion, // : ReleaseStep
  publishArtifactsSigned,
  finishReleaseAtSonatype,
  commitReleaseVersion, // : ReleaseStep, performs the initial git checks
  tagRelease, // : ReleaseStep
  setNextVersion, // : ReleaseStep
  commitNextVersion, // : ReleaseStep
  pushChanges // : ReleaseStep, also checks that an upstream branch is properly configured
)

pomExtra := SbtSonatypeHelpers.githubPom(name.value)

publishTo := SbtSonatypeHelpers.publishToGenerator(version.value)

publishArtifact in Test := false

pomPostProcess := PackagingHelpers.removeTestOrSourceDependencies