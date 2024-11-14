package io.github.khanr1.scalaopenai

import cats.effect.kernel.Resource
import io.github.khanr1.scalaopenai.OpenAIClient
import io.github.khanr1.scalaopenai.client.http4sImpl.OpenAIClientHttp4s
import org.http4s.ember.client.EmberClientBuilder
import io.github.khanr1.scalaopenai.OpenAIService

/** Factory object for constructing an `OpenAIService` using an Http4s client.
  */
object OpenAI:
  /** Creates an `OpenAIService` for interacting with the OpenAI API.
    *
    * @param apiKey
    *   The API key used for authenticating with OpenAI.
    * @tparam F
    *   The effect type (e.g., IO, Future) with Async capabilities.
    * @return
    *   A resource that, when allocated, provides an `OpenAIService`.
    */
  def make[F[_]: cats.effect.Async](apiKey: String): Resource[F, OpenAIService[F]] =
    EmberClientBuilder.default[F].build.flatMap { httpClient =>
      val client = OpenAIClientHttp4s.make[F](httpClient, apiKey = apiKey)
      val service = OpenAIService.make[F](client)
      Resource.pure(service)
    }
