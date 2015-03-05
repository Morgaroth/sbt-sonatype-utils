package io.github.morgaroth.sbt

import sbt._

/**
 * `SbtSonatypeUtils` is list of helpers for dealing with configuring project to be released to oss.sonatype
 */


trait SbtSonatypeUtils {

  /**
   * `githubPom` is helper method for generating part of maven pom needed by sonatype for project hosted on github
   *
   * @param projectName name of project on github, from which github http links will be generated to put inside pom
   * @param developerName developer name, this may be every string, mostly this is name and surname of developer
   * @param developerId developer id, in case of this method it must be a github nick of developer, is used for generation github links
   * @return complete POM needed for publishing to oss.sonatype
   */
  def githubPom(projectName: String, developerName: String, developerId: String) = pom(
    s"https://github.com/$developerId/$projectName",
    s"git@github.com:$developerId/$projectName.git",
    developerName,
    developerId
  )

  /**
   * `pom` method generates part of maven pom needed by sonatype from important in this pom inputs, like:
   *
   * @param projectUrl url to project, mostly http to project page or repo
   * @param developerUrl developer connection to repository, mostly ssh repo link for `git clone`
   * @param developerName developer name, this may be every string, mostly this is name and surname of developer
   * @param developerId developer id, in case of this method it must be a github nick of developer, is used for generation github links
   * @return complete POM needed for publishing to oss.sonatype
   */
  def pom(projectUrl: String, developerUrl: String, developerName: String, developerId: String) = {
//@formatter:off
    <url>{projectUrl}</url>
      <licenses>
        <license>
          <name>BSD-style</name>
          <url>http://www.opensource.org/licenses/bsd-license.php</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>{developerUrl}</url>
        <connection>scm:git:{developerUrl}</connection>
      </scm>
      <developers>
        <developer>
          <id>{developerName}</id>
          <name>{developerId}</name>
        </developer>
      </developers>
//@formatter:on
  }

  def publishRepoForVersion = (ver: String) => {
    val nexus = "https://oss.sonatype.org/"
    if (ver.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}

object SbtSonatypeUtils extends AutoPlugin {

  object autoImport extends SbtSonatypeUtils

}