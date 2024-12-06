package io.github.khanr1.scalaopenai

import io.github.khanr1.scalaopenai.chat.Message
import io.github.khanr1.scalaopenai.chat.ChatCompletionResponse
import io.github.khanr1.scalaopenai.OpenAIResponse
import io.github.khanr1.scalaopenai.chat.{ChatCompletionResponse, Message}

/** Trait representing an OpenAI client that supports chat completions.
  *
  * @tparam F
  *   The effect type (e.g., IO, Future) used to manage side effects.
  */
trait OpenAIClient[F[_]] {

  /** Sends a chat completion request to OpenAI's API and returns a stream of responses.
    *
    * @param messages
    *   The list of messages representing the conversation history.
    * @return
    *   A stream of `ChatCompletionResponse` objects.
    */
  def chatCompletion(
      model: Models = Models.GPT4omin,
      messages: List[Message]
  ): Stream[F, OpenAIResponse]
}
