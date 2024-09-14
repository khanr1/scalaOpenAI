package com.khanr1.scalaopenai
package client
package http4sImpl

import com.khanr1.scalaopenai.chat.ChatCompletionResponse
import com.khanr1.scalaopenai.chat.Message

import com.khanr1.scalaopenai.chat.ChatCompletionRequest
import io.circe.syntax.*
import io.circe.parser.*
import org.http4s.{AuthScheme, Method, Headers, MediaType, Uri, Credentials}
import org.http4s.circe.*
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.headers.{Authorization, `Content-Type`}
import fs2.text

object OpenAIClientHttp4s {
  def make[F[_]: cats.effect.Concurrent](client: Client[F], apiKey: String): OpenAIClient[F] =
    new OpenAIClient[F] with Http4sClientDsl[F] {

      private val chatUri: Uri = Uri.unsafeFromString("https://api.openai.com/v1/chat/completions")
      override def chatCompletion(messages: List[Message]): Stream[F, ChatCompletionResponse] =
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
          response.body
            .through(fs2.text.utf8.decode)
            .flatMap { text =>
              // Split the response on newlines in case multiple `data:` chunks are in the same line
              Stream.emits(text.split("\n").toSeq) // Convert to a stream of individual lines
            }
            .map(_.replace("data: ", ""))
            .filterNot(line => line.isEmpty || line == "[DONE]")
            .flatMap { text =>
              parse(text) match {
                case Right(json) =>
                  json.as[ChatCompletionResponse] match {
                    case Right(chatCompletion) =>
                      Stream.emit(chatCompletion) // Emit the parsed response
                    case Left(decodingFailure) =>
                      print(text)
                      Stream.raiseError[F](
                        new Exception(
                          s"Decoding failure for : ${decodingFailure.getMessage} "
                        )
                      )
                  }
                case Left(parseFailure) =>
                  Stream
                    .raiseError[F](new Exception(s"Parsing failure: ${parseFailure.getMessage}"))
              }
            }

        }
    }
}
