name := "milp-solver"

organization := "org.ow2.paasage"

val suffix = settingKey[String]("Version suffix, i.e. branch name")

val paasageVersion = settingKey[String]("PaaSage version")

paasageVersion := sys.props.getOrElse("paasage.version", default = "2016.4.0-SNAPSHOT")

suffix := sys.props.getOrElse("suffix", default = "SNAPSHOT")

version := "2016.4.0" + "-" + suffix.value

mainClass := Some("eu.paasage.upperware.milp_solver.exec.Daemon")

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
  ("org.ow2.paasage.mddb.cdo"   % "client"               % "2015.9.1-SNAPSHOT").exclude("org.eclipse.runtime", "runtime-registry-compatibility").exclude("asm", "asm"),
  "org.ow2.paasage"             % "upperware-metamodel"  % "2016.4.0-SNAPSHOT" intransitive(),
  "com.typesafe.scala-logging" %% "scala-logging-slf4j"  % "2.1.2",
  "com.typesafe"                %  "config"              % "1.2.1",
  "ch.qos.logback"              % "logback-classic"      % "1.1.2"
)

publishTo := {
  if (isSnapshot.value)
    Some("Sonatype Nexus Repository Manager" at "http://repository.ow2.org/nexus/content/repositories/snapshots/")
  else
    Some("Sonatype Nexus Repository Manager" at "http://repository.ow2.org/nexus/content/repositories/releases/")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")


crossPaths := false
