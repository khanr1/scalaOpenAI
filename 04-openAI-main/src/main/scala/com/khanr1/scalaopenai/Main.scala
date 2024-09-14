package com.khanr1.scalaopenai

import cats.effect.IOApp
import cats.effect.IO
import com.khanr1.scalaopenai.chat.Message

object Main extends IOApp.Simple {
  def run: IO[Unit] = {
    val apiKey = "gitKeyHere"
    OpenAI.make[IO](apiKey = apiKey).use { service =>
      service
        .chatCompletion(List(Message(Roles.User, "Tell me a joke")))
        .evalMap(response => IO.print(response.choices.map(_.delta.content.mkString).mkString))
        .compile
        .drain
    }

  }
}
