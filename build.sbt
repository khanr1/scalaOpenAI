import Dependencies.*
import MyUtils.*

ThisBuild / version := "0.0.1-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.3"
ThisBuild / organization := "com.khanr1"

githubOwner := "khanr1"
githubRepository := "scalaOpenAI"
githubTokenSource := TokenSource.GitConfig("github.token")

lazy val `scalaopenai` =
  project
    .in(file("."))
    .aggregate(domain, core, client, main)
    .settings(
      name := "ScalaOpenAI"
    )

lazy val domain =
  project
    .in(file("01-openAI-domain"))
    .settings(testDependencies)
    .settings(
      libraryDependencies ++= Seq(
        Libraries.cats.value,
        Libraries.catsEffect.value,
        Libraries.circe.value,
        Libraries.circeParser.value
      )
    )

lazy val core =
  project
    .in(file("02-openAI-core"))
    .dependsOn(domain % Cctt)
    .settings(
      libraryDependencies ++= Seq(
        Libraries.htt4sEmberClient.value,
        Libraries.circeParser.value
      )
    )
lazy val client =
  project
    .in(file("03-openAI-client"))
    .dependsOn(core % Cctt)
    .settings(
      libraryDependencies ++= Seq(
        Libraries.htt4sCirce.value,
        Libraries.htt4sDsl.value,
        Libraries.htt4sEmberClient.value,
        Libraries.circeParser.value
      )
    )

lazy val main =
  project
    .in(file("04-openAI-main"))
    .dependsOn(client % Cctt)
    .settings(
      libraryDependencies ++= Seq(
        Libraries.ciris
      )
    )

lazy val testDependencies = Seq(
  testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
  libraryDependencies ++= Seq(
    Libraries.weaverCats % Test,
    Libraries.weaverDiscipline % Test,
    Libraries.weaverScalaCheck % Test
  )
)

addCommandAlias("run", "main/run")
addCommandAlias("reStart", "main/reStart")
