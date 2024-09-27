package com.khanr1.scalaopenai

import com.khanr1.scalaopenai.chat.Message
import com.khanr1.scalaopenai.chat.ChatCompletionResponse

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
  def chatCompletion(messages: List[Message]): Stream[F, OpenAIResponse]
}
