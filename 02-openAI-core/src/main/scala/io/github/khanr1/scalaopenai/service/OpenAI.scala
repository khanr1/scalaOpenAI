package io.github.khanr1.scalaopenai

import io.github.khanr1.scalaopenai.chat.*
import io.github.khanr1.scalaopenai.OpenAIResponse
import io.github.khanr1.scalaopenai.chat.Message

/** Service for interacting with the OpenAI API for chat completions.
  *
  * @tparam F
  *   Effect type, such as IO or Future.
  */
trait OpenAIService[F[_]] {

  /** Sends a list of messages and returns a stream of chat completion responses.
    *
    * @param messages
    *   The list of messages representing the conversation.
    * @return
    *   A stream of `ChatCompletionResponse` objects.
    */
  def chatCompletion(
      model: Models = Models.GPT4omin,
      messages: List[Message]
  ): Stream[F, OpenAIResponse]
}

object OpenAIService:
  /** Creates an instance of `OpenAIService`, using the given `OpenAIClient`.
    *
    * @param client
    *   The OpenAI client used to perform the API requests.
    * @param F
    *   Implicit evidence of a concurrent effect type (such as IO or Future).
    * @return
    *   An instance of `OpenAIService`.
    */
  def make[F[_]: cats.effect.Concurrent](client: OpenAIClient[F]): OpenAIService[F] =
    new OpenAIService[F] {

      override def chatCompletion(
          model: Models = Models.GPT4omin,
          messages: List[Message]
      ): Stream[F, OpenAIResponse] =
        client.chatCompletion(model, messages)

    }
