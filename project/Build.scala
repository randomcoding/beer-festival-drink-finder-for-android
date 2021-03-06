import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Beer Festival Drink Finder",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-10"
  )

  val proguardSettings = Seq (
    useProguard in Android := true,
    proguardOption in Android := "-keep class scala.Function1"
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me", // TODO: Make this dreived from a variable read from a file
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"
    )

  lazy val eclipseAndroidSettings: Seq[Setting[_]] = inConfig(Android) (
    Seq(
      manifestPath <<= (baseDirectory, manifestName) map((s,m) => Seq(s / m)) map (x=>x),
      mainResPath <<= (baseDirectory, resDirectoryName) (_ / _) map (x=>x),
      mainAssetsPath <<= (baseDirectory, assetsDirectoryName) (_ / _)
    )
  )
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
