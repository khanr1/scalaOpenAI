package com.khanr1.scalaopenai

import cats.effect.kernel.Resource
import com.khanr1.scalaopenai.OpenAIClient
import com.khanr1.scalaopenai.client.http4sImpl.OpenAIClientHttp4s
import org.http4s.ember.client.EmberClientBuilder
import com.khanr1.scalaopenai.OpenAIService

object OpenAI:
  def make[F[_]: cats.effect.Async](apiKey: String): Resource[F, OpenAIService[F]] =
    EmberClientBuilder.default[F].build.flatMap { httpClient =>
      val client = OpenAIClientHttp4s.make[F](httpClient, apiKey = apiKey)
      val service = OpenAIService.make[F](client)
      Resource.pure(service)
    }
