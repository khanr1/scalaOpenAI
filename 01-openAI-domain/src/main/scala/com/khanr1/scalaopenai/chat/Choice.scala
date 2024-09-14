package com.khanr1.scalaopenai
package chat

/** Case class representing a choice made during the generation process in OpenAI's API.
  *
  * @param index
  *   Position of the choice in the returned list of choices.
  * @param delta
  *   The `Delta` object representing partial or full message content.
  * @param logprobs
  *   Optional Boolean indicating whether log probabilities are returned.
  * @param finish_reason
  *   Optional String representing why the generation process finished (e.g., max tokens reached).
  */
final case class Choice(
    index: Int,
    delta: Delta,
    logprobs: Option[Boolean] = None,
    finish_reason: Option[String]
)

object Choice:
  /** Circe Decoder for Choice that decodes JSON into a Choice object.
    */
  given Decoder[Choice] = Decoder.derived
  given Encoder[Choice] = Encoder.derived
