name := "milp-solver"

organization := "wp3"

version := "2015.04-SNAPSHOT"

mainClass := Some("eu.paasage.upperware.milp_solver.MainCDO")

scalaVersion := "2.11.1"

resolvers ++= Seq(
    "CDO Client @ jenkins" at "http://jenkins.paasage.cetic.be/job/CDO_CLIENT/lastBuild/maven-repository/repository/",
    "WP3 @ jenkins" at "http://jenkins.paasage.cetic.be/job/WP3_MODEL/lastBuild/maven-repository/repository/",
    "CAMEL @ jenkins" at "http://jenkins.paasage.cetic.be/job/CAMEL/lastBuild/maven-repository/repository/",
    "PaaSage Maven Repository" at "http://jenkins.paasage.cetic.be/repository/"
)

publishTo := Some(Resolver.file("file",  new File(s"${Path.userHome.absolutePath}/.m2/repository")))

libraryDependencies ++= Seq(
  "org.apache.xmlrpc" % "xmlrpc-client" % "3.1.3", // required by jCMPL.jar
  "org.apache.commons" % "commons-lang3" % "3.0", // required by jCMPL.jar
  "cmpl" % "jcmpl" % "1.10.0",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  ("eu.paasage.mddb.cdo" % "client" % "2.0.0-SNAPSHOT").exclude("org.eclipse.runtime", "runtime-registry-compatibility"),
 "wp3" % "wp3-cp-models" % "2.0.0-SNAPSHOT" intransitive(),
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "com.typesafe" %  "config" % "1.2.1",
  "ch.qos.logback" % "logback-classic" % "1.1.2"
)

