package com.khanr1.scalaopenai
package client
package http4sImpl

import weaver.*
import cats.effect.IO
import org.http4s.Response
import org.http4s.HttpRoutes
import org.http4s.dsl.io.*
import org.http4s.client.Client
import com.khanr1.scalaopenai.chat.ChatCompletionResponse
import io.circe.syntax.*
import org.http4s.circe.*
import weaver.scalacheck.Checkers

import com.khanr1.scalaopenai.Generators.chatCompletionResponse
import com.khanr1.scalaopenai.Generators.chatCompletionRequestGen
import org.http4s.dsl.request
import com.khanr1.scalaopenai.Generators.openAIErrorGen
import com.khanr1.scalaopenai.Generators.OpenAIErrorResponseGen

object OpenAIClientHttp4sSuites extends SimpleIOSuite with Checkers {

  val gen = for
    request <- chatCompletionRequestGen
    response <- chatCompletionResponse
  yield request -> response

  val genError = for
    request <- chatCompletionRequestGen
    response <- OpenAIErrorResponseGen
  yield request -> response

  def routes(mkResponse: IO[Response[IO]]) = HttpRoutes
    .of[IO] { case POST -> Root / "v1" / "chat" / "completions" =>
      mkResponse
    }
    .orNotFound

  test("Response OK") {
    forall(gen) { (request, response) =>
      def client1(chatResp: ChatCompletionResponse) =
        Client.fromHttpApp(routes(Ok(chatResp.asJson)))
      OpenAIClientHttp4s
        .make(client1(response), "test")
        .chatCompletion(request.messages)
        .compile
        .toList
        .map(_.headOption)
        .map(expect.same(_, Some(response)))

    }
  }
  test("Response BadRequest") {
    forall(genError) { (request, response) =>
      def client(chatResp: OpenAIErrorResponse) =
        Client.fromHttpApp(routes(BadRequest(chatResp.asJson)))
      OpenAIClientHttp4s
        .make(client(response), "test")
        .chatCompletion(request.messages)
        .compile
        .toList
        .map(_.headOption)
        .map(expect.same(_, Some(response)))
    }

  }

}
