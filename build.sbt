name := "milp-solver"

organization := "wp3"

version := "2015.04-SNAPSHOT"

mainClass := Some("eu.paasage.upperware.milp_solver.MainCDO")

scalaVersion := "2.11.1"

resolvers ++= Seq(
  "OW2 repo"                 at "http://repository.ow2.org/nexus/content/repositories/snapshots/",
  "PaaSage Maven Repository" at "http://jenkins.paasage.cetic.be/repository/",
  "Sonatype (releases)"      at "https://oss.sonatype.org/content/repositories/releases/"
)

publishTo := Some(Resolver.file("file",  new File(s"${Path.userHome.absolutePath}/.m2/repository")))

libraryDependencies ++= Seq(
  "org.zeromq"                  % "jeromq"               % "0.3.5",
  "org.apache.xmlrpc"           % "xmlrpc-client"        % "3.1.3", // required by jCMPL.jar
  "org.apache.commons"          % "commons-lang3"        % "3.0", // required by jCMPL.jar
  "cmpl"                        % "jcmpl"                % "1.10.0",
  "org.scala-lang"              % "scala-reflect"        % scalaVersion.value,
  ("org.ow2.paasage.mddb.cdo"   % "client"               % paasageVersion.value).exclude("org.eclipse.runtime", "runtime-registry-compatibility").exclude("asm", "asm"),
  "org.ow2.paasage"             % "upperware-metamodel"  % paasageVersion.value intransitive(),
  "com.typesafe.scala-logging" %% "scala-logging-slf4j"  % "2.1.2",
  "com.typesafe"                %  "config"              % "1.2.1",
  "ch.qos.logback"              % "logback-classic"      % "1.1.2"
)

