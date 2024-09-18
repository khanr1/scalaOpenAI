package com.khanr1.scalaopenai
package client
package http4sImpl

import com.khanr1.scalaopenai.chat.ChatCompletionResponse
import com.khanr1.scalaopenai.chat.Message
import utils.*

import com.khanr1.scalaopenai.chat.ChatCompletionRequest
import io.circe.syntax.*
import io.circe.parser.*
import org.http4s.{AuthScheme, Method, Headers, MediaType, Uri, Credentials}
import org.http4s.circe.*
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.headers.{Authorization, `Content-Type`}
import fs2.text
import org.http4s.Status
import cats.MonadThrow

/** An OpenAI client implemented with Http4s for making chat completion requests.
  */
object OpenAIClientHttp4s {

  /** Creates an instance of the `OpenAIClient` using Http4s.
    *
    * @param client
    *   The Http4s client to perform HTTP requests.
    * @param apiKey
    *   The API key used for authorization with OpenAI.
    * @tparam F
    *   The effect type (e.g., IO, Future).
    * @return
    *   An instance of `OpenAIClient` that performs chat completions.
    */
  def make[F[_]: cats.effect.Concurrent: MonadThrow](
      client: Client[F],
      apiKey: String
  ): OpenAIClient[F] =
    new OpenAIClient[F] with Http4sClientDsl[F] {

      private val chatUri: Uri = Uri.unsafeFromString("https://api.openai.com/v1/chat/completions")
      override def chatCompletion(messages: List[Message]): Stream[F, OpenAIResponse] =
        val requestBody = ChatCompletionRequest(
          Models.GPT4o,
          messages,
          stream = true
        )
        val request = Method.POST(
          requestBody.asJson,
          chatUri,
          Authorization(Credentials.Token(AuthScheme.Bearer, apiKey)),
          `Content-Type`(MediaType.application.json)
        )

        client.stream(request).flatMap { response =>
          response.status match
            case Status.Ok =>
              response.bodyText.through(parseResponsePipe[F, ChatCompletionResponse])
            case status =>
              response.bodyText
                .through(parseResponsePipe[F, OpenAIErrorResponse])

        }
    }

}
