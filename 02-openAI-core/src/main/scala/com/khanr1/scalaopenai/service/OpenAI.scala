package com.khanr1.scalaopenai

import com.khanr1.scalaopenai.chat.*
import cats.effect.kernel.Resource
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.client.Client

trait OpenAIService[F[_]] {
  def chatCompletion(messages: List[Message]): Stream[F, ChatCompletionResponse]
}

object OpenAIService:
  def make[F[_]: cats.effect.Concurrent](client: OpenAIClient[F]): OpenAIService[F] =
    new OpenAIService[F] {

      override def chatCompletion(messages: List[Message]): Stream[F, ChatCompletionResponse] =
        client.chatCompletion(messages)

    }
