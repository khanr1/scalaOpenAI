package com.khanr1.scalaopenai
package chat

/** Represents a request to the OpenAI API for chat completions.
  *
  * @param model
  *   The model to use for the request (e.g., GPT-4).
  * @param messages
  *   A list of messages representing the conversation so far.
  * @param temperature
  *   A value between 0.0 and 2.0 that controls the randomness of the output.
  * @param stream
  *   A boolean indicating whether the response should be streamed in real time.
  */
final case class ChatCompletionRequest(
    model: Models,
    messages: List[Message],
    temperature: Double = 1.0,
    stream: Boolean = false
) {
  // Ensures that temperature is within the allowed range
  require(temperature >= 0.0 && temperature <= 2.0, "Temperature must be between 0.0 and 2.0")
}

object ChatCompletionRequest:
  // Circe Encoder and Decoder for the ChatCompletionRequest case class
  given Encoder[ChatCompletionRequest] = Encoder.derived[ChatCompletionRequest]
  given Decoder[ChatCompletionRequest] = Decoder.derived[ChatCompletionRequest]
