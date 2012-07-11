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
      keyalias in Android := "change-me",
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
  lazy val main = Project (
    "Beer Festival Drink Finder",
    file("."),
    settings = General.fullAndroidSettings ++ General.eclipseAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "Beer Festival Drink FinderTests"
    ) ++ General.eclipseAndroidSettings
  ) dependsOn main
}
