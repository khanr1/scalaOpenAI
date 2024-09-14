package com.khanr1.scalaopenai
package chat

/** Case class representing token usage in an OpenAI chat interaction.
  *
  * @param prompt_tokens
  *   Number of tokens used in the prompt.
  * @param completion_tokens
  *   Number of tokens used in the completion (response).
  * @param total_tokens
  *   Total number of tokens used (sum of prompt and completion).
  */
final case class Usage(prompt_tokens: Int, completion_tokens: Int, total_tokens: Int)

object Usage:
  /** Decoder instance for Usage, allowing the class to be decoded from JSON. Handles cases where
    * fields might be missing or incorrectly formatted.
    */
  given Decoder[Usage] = Decoder.derived
  given Encoder[Usage] = Encoder.derived
