package io.github.khanr1.scalaopenai

import cats.effect.IOApp
import cats.effect.IO
import io.github.khanr1.scalaopenai.chat.Message
import io.github.khanr1.scalaopenai.Roles
import io.github.khanr1.scalaopenai.chat.Message
import io.github.khanr1.scalaopenai.Config.loadOpenAIConfig

object Main extends IOApp.Simple {
  def run: IO[Unit] = {
    val apiKey = loadOpenAIConfig().load[IO]

    apiKey.flatMap { key =>
      OpenAI.make[IO](apiKey = key.apiKey).use { service =>
        service
          .chatCompletion(
            List(
              Message(
                Roles.User,
                "Give the date and time and then Write a loremlisum text of 100 words"
              )
            )
          )
          .evalMap(response => IO.print(response.getResponseMessage))
          .compile
          .drain
      }
    }

  }
}
