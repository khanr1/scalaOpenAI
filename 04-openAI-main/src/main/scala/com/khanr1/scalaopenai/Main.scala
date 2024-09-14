package com.khanr1.scalaopenai

import cats.effect.IOApp
import cats.effect.IO
import com.khanr1.scalaopenai.chat.Message
import com.khanr1.scalaopenai.Config.loadOpenAIConfig

object Main extends IOApp.Simple {
  def run: IO[Unit] = {
    val apiKey = loadOpenAIConfig().load[IO]

    apiKey.flatMap { key =>
      OpenAI.make[IO](apiKey = key.apiKey).use { service =>
        service
          .chatCompletion(List(Message(Roles.User, "Tell me a joke")))
          .evalMap(response => IO.print(response.choices.map(_.delta.content.mkString).mkString))
          .compile
          .drain
      }
    }

  }
}
