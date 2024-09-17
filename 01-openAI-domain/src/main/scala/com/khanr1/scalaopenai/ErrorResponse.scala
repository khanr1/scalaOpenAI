package com.khanr1.scalaopenai

/** Define error case class
  *
  * @param message
  *   The message associated to the error
  * @param type
  *   The type of the Error from the `OpenAIErrors`
  * @param param
  *   Provide information about whit parameter in the request caused the error
  * @param code
  *   provides more detailed information about the type of error encountered
  */
final case class ErrorResponse(
    message: String,
    `type`: OpenAIErrorResponse,
    param: Option[String],
    code: Option[String]
) extends Throwable

object Error:
  given Encoder[ErrorResponse] = Encoder.derived
  given Decoder[ErrorResponse] = Decoder.derived
