import sbt._

import Keys._
import AndroidKeys._

import scala.io.Source

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Beer Festival Drink Finder",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-10"
  )

  val keptClasses = Seq("scala.Function1", "scala.Tuple2", "scala.collection.Seq", "scala.Option", "scala.Function2", "scala.collection.immutable.Map",
    "scala.collection.immutable.List", "scala.Enumeration$Value")

  val proguardSettings = Seq (
    useProguard in Android := true,
    proguardOption in Android := "-keep class %s".format(keptClasses.mkString(", "))
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := keyAlias,
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"
    )

  lazy val eclipseAndroidSettings: Seq[Setting[_]] = inConfig(Android) (
    Seq(
      manifestPath <<= (baseDirectory, manifestName) map((s,m) => Seq(s / m)) map (x=>x),
      mainResPath <<= (baseDirectory, resDirectoryName) (_ / _) map (x=>x),
      mainAssetsPath <<= (baseDirectory, assetsDirectoryName) (_ / _)
    )
  )

  // Read the key alias for the key store from the key.properties file
  lazy val keyAlias = Source.fromFile("keystore.properties").getLines.toList.filter(_.startsWith("key.alias")) match {
    case head :: _ => head.split("=")(1)
    case Nil => "unknown key"
  }
}

object AndroidBuild extends Build {
  lazy val root = Project (
    "root",
    file("."),
    settings = General.fullAndroidSettings ++ General.eclipseAndroidSettings
  ) aggregate(client, tests)

  lazy val client = Project (
    "client",
    file("client"),
    settings = General.fullAndroidSettings ++ General.eclipseAndroidSettings ++ Seq (
      name := "Beer Festival Drink Finder"
    )
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "Beer Festival Drink FinderTests"
    ) ++ General.eclipseAndroidSettings
  ) dependsOn client
}
